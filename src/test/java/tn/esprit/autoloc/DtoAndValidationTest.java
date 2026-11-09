package tn.esprit.autoloc;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import tn.esprit.autoloc.domain.*;
import tn.esprit.autoloc.dto.*;
import tn.esprit.autoloc.exception.ResourceNotFoundException;
import tn.esprit.autoloc.mapper.*;
import tn.esprit.autoloc.web.controller.GlobalExceptionHandler;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Map;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class DtoAndValidationTest {

    private static Validator validator;
    private static GlobalExceptionHandler exceptionHandler;

    @BeforeAll
    public static void setUp() {
        ValidatorFactory factory = Validation.byDefaultProvider()
                .configure()
                .messageInterpolator(new org.hibernate.validator.messageinterpolation.ParameterMessageInterpolator())
                .buildValidatorFactory();
        validator = factory.getValidator();
        exceptionHandler = new GlobalExceptionHandler();
    }

    @Test
    public void testVehiculeValidationSuccess() {
        VehiculeDTO dto = VehiculeDTO.builder()
                .immatriculation("123-TN-456")
                .marque("Renault")
                .modele("Clio")
                .categorie(CategorieVehicule.CITADINE)
                .tarifJournalier(new BigDecimal("90.00"))
                .statut(StatutVehicule.DISPONIBLE)
                .agenceId(1L)
                .build();

        Set<ConstraintViolation<VehiculeDTO>> violations = validator.validate(dto);
        assertTrue(violations.isEmpty(), "Should have no violations");
    }

    @Test
    public void testVehiculeValidationErrors() {
        VehiculeDTO dto = VehiculeDTO.builder()
                .immatriculation("") // blank
                .marque(null)       // null
                .modele(" ")        // blank
                .categorie(null)    // null
                .tarifJournalier(new BigDecimal("-10.00")) // negative
                .statut(null)       // null
                .build();

        Set<ConstraintViolation<VehiculeDTO>> violations = validator.validate(dto);
        assertFalse(violations.isEmpty(), "Should have validation errors");
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("immatriculation")));
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("marque")));
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("modele")));
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("tarifJournalier")));
    }

    @Test
    public void testClientValidation() {
        ClientDTO invalidClient = ClientDTO.builder()
                .nom("")
                .prenom("")
                .email("invalid-email")
                .telephone("abc")
                .numPermis("")
                .dateInscription(LocalDate.now().plusDays(5)) // future
                .build();

        Set<ConstraintViolation<ClientDTO>> violations = validator.validate(invalidClient);
        assertFalse(violations.isEmpty());
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("email")));
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("dateInscription")));
    }

    @Test
    public void testContratAndPaiementValidation() {
        ContratDTO invalidContrat = ContratDTO.builder()
                .dateSignature(null)
                .montantTotal(new BigDecimal("-50.00"))
                .reservationId(null)
                .build();
        Set<ConstraintViolation<ContratDTO>> contratViolations = validator.validate(invalidContrat);
        assertFalse(contratViolations.isEmpty());
        assertTrue(contratViolations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("reservationId")));
        assertTrue(contratViolations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("montantTotal")));

        PaiementDTO invalidPaiement = PaiementDTO.builder()
                .montant(new BigDecimal("-100.00"))
                .datePaiement(LocalDate.now().plusDays(2))
                .modePaiement(null)
                .contratId(null)
                .build();
        Set<ConstraintViolation<PaiementDTO>> paiementViolations = validator.validate(invalidPaiement);
        assertFalse(paiementViolations.isEmpty());
        assertTrue(paiementViolations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("montant")));
        assertTrue(paiementViolations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("datePaiement")));
    }

    @Test
    public void testVehiculeMapper() {
        VehiculeMapper mapper = new VehiculeMapper();

        Agence agence = new Agence();
        agence.setIdAgence(5L);
        agence.setNom("Agence Centrale");

        Vehicule vehicule = new Vehicule();
        vehicule.setIdVehicule(10L);
        vehicule.setImmatriculation("200-TN-100");
        vehicule.setMarque("Peugeot");
        vehicule.setModele("208");
        vehicule.setCategorie(CategorieVehicule.BERLINE);
        vehicule.setTarifJournalier(new BigDecimal("120.00"));
        vehicule.setStatut(StatutVehicule.DISPONIBLE);
        vehicule.setAgence(agence);

        VehiculeDTO dto = mapper.toDto(vehicule);

        assertNotNull(dto);
        assertEquals(10L, dto.getIdVehicule());
        assertEquals("200-TN-100", dto.getImmatriculation());
        assertEquals("Peugeot", dto.getMarque());
        assertEquals(5L, dto.getAgenceId());
        assertEquals("Agence Centrale", dto.getAgenceNom());

        Vehicule entity = mapper.toEntity(dto);
        assertEquals(dto.getImmatriculation(), entity.getImmatriculation());
        assertEquals(dto.getTarifJournalier(), entity.getTarifJournalier());
    }

    @Test
    public void testAgenceMapper() {
        AgenceMapper mapper = new AgenceMapper();
        Agence agence = new Agence();
        agence.setIdAgence(1L);
        agence.setNom("Agence Tunis");
        agence.setVille("Tunis");
        agence.setAdresse("Avenue Habib Bourguiba");
        agence.setTelephone("+216 71 123 456");

        AgenceDTO dto = mapper.toDto(agence);
        assertEquals("Agence Tunis", dto.getNom());
        assertEquals(0, dto.getNombreVehicules());

        Agence entity = mapper.toEntity(dto);
        assertEquals("Tunis", entity.getVille());
    }

    @Test
    public void testReservationAndContratMapper() {
        ReservationMapper resMapper = new ReservationMapper();
        Client client = new Client();
        client.setIdClient(3L);
        client.setNom("Ben Salah");
        client.setPrenom("Karim");

        Vehicule vehicule = new Vehicule();
        vehicule.setIdVehicule(7L);
        vehicule.setMarque("Toyota");
        vehicule.setModele("Yaris");
        vehicule.setImmatriculation("180-TN-999");

        Reservation reservation = new Reservation();
        reservation.setIdReservation(1L);
        reservation.setDateDebut(LocalDate.now());
        reservation.setDateFin(LocalDate.now().plusDays(3));
        reservation.setStatut(StatutReservation.CONFIRMEE);
        reservation.setClient(client);
        reservation.setVehicule(vehicule);

        ReservationDTO resDto = resMapper.toDto(reservation);
        assertNotNull(resDto);
        assertEquals(1L, resDto.getIdReservation());
        assertEquals(3L, resDto.getClientId());
        assertEquals("Karim Ben Salah", resDto.getClientNomComplet());
        assertEquals(7L, resDto.getVehiculeId());

        ContratMapper contMapper = new ContratMapper();
        Contrat contrat = new Contrat();
        contrat.setIdContrat(4L);
        contrat.setDateSignature(LocalDate.now());
        contrat.setMontantTotal(new BigDecimal("300.00"));
        contrat.setValide(true);
        contrat.setReservation(reservation);

        ContratDTO contDto = contMapper.toDto(contrat);
        assertNotNull(contDto);
        assertEquals(4L, contDto.getIdContrat());
        assertEquals(1L, contDto.getReservationId());
        assertTrue(contDto.isValide());
    }

    @Test
    @SuppressWarnings("unchecked")
    public void testGlobalExceptionHandlerValidationErrors() {
        VehiculeDTO target = new VehiculeDTO();
        BeanPropertyBindingResult bindingResult = new BeanPropertyBindingResult(target, "vehiculeDTO");
        bindingResult.addError(new FieldError("vehiculeDTO", "immatriculation", "L'immatriculation est obligatoire"));
        bindingResult.addError(new FieldError("vehiculeDTO", "tarifJournalier", "Le tarif journalier doit être supérieur à zéro"));

        MethodArgumentNotValidException ex = new MethodArgumentNotValidException(null, bindingResult);
        ResponseEntity<Map<String, Object>> response = exceptionHandler.handleValidationExceptions(ex);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        Map<String, Object> body = response.getBody();
        assertNotNull(body);
        assertEquals(400, body.get("status"));
        assertEquals("Bad Request", body.get("error"));
        assertEquals("Erreur de validation des données", body.get("message"));

        Map<String, String> errors = (Map<String, String>) body.get("errors");
        assertNotNull(errors);
        assertEquals("L'immatriculation est obligatoire", errors.get("immatriculation"));
        assertEquals("Le tarif journalier doit être supérieur à zéro", errors.get("tarifJournalier"));
    }

    @Test
    public void testGlobalExceptionHandlerNotFound() {
        ResourceNotFoundException ex = new ResourceNotFoundException("Vehicule", 999L);
        ResponseEntity<Map<String, Object>> response = exceptionHandler.handleNotFound(ex);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        Map<String, Object> body = response.getBody();
        assertNotNull(body);
        assertEquals(404, body.get("status"));
        assertEquals("Not Found", body.get("error"));
        assertTrue(body.get("message").toString().contains("introuvable"));
    }

    @Test
    public void testGlobalExceptionHandlerBadRequest() {
        IllegalArgumentException ex = new IllegalArgumentException("Identifiant invalide");
        ResponseEntity<Map<String, Object>> response = exceptionHandler.handleBadRequest(ex);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        Map<String, Object> body = response.getBody();
        assertNotNull(body);
        assertEquals(400, body.get("status"));
        assertEquals("Identifiant invalide", body.get("message"));
    }

    public static void main(String[] args) {
        setUp();
        DtoAndValidationTest test = new DtoAndValidationTest();
        System.out.println("======================================================================");
        System.out.println("        EXECUTION DES TESTS UNITAIRES DTO, MAPPERS & VALIDATION       ");
        System.out.println("======================================================================");
        try {
            test.testVehiculeValidationSuccess();
            System.out.println("[PASS] 1. Validation VehiculeDTO valide : OK (0 violation)");

            test.testVehiculeValidationErrors();
            System.out.println("[PASS] 2. Validation VehiculeDTO invalide : OK (@NotBlank, @Positive, @NotNull)");

            test.testClientValidation();
            System.out.println("[PASS] 3. Validation ClientDTO invalide : OK (@Email, @PastOrPresent)");

            test.testContratAndPaiementValidation();
            System.out.println("[PASS] 4. Validation ContratDTO & PaiementDTO : OK (@PositiveOrZero, @Positive)");

            test.testVehiculeMapper();
            System.out.println("[PASS] 5. VehiculeMapper (Vehicule <-> VehiculeDTO avec Agence) : OK");

            test.testAgenceMapper();
            System.out.println("[PASS] 6. AgenceMapper (Agence <-> AgenceDTO) : OK");

            test.testReservationAndContratMapper();
            System.out.println("[PASS] 7. ReservationMapper & ContratMapper (avec relations) : OK");

            test.testGlobalExceptionHandlerValidationErrors();
            System.out.println("[PASS] 8. GlobalExceptionHandler (MethodArgumentNotValidException -> 400 + Map errors) : OK");

            test.testGlobalExceptionHandlerNotFound();
            System.out.println("[PASS] 9. GlobalExceptionHandler (ResourceNotFoundException -> 404) : OK");

            test.testGlobalExceptionHandlerBadRequest();
            System.out.println("[PASS] 10. GlobalExceptionHandler (IllegalArgumentException -> 400) : OK");

            System.out.println("======================================================================");
            System.out.println("🎉 TOUS LES TESTS (10/10) SONT PASSES AVEC SUCCES !");
            System.out.println("======================================================================");
        } catch (Throwable t) {
            System.err.println("❌ ECHEC : " + t.getMessage());
            t.printStackTrace();
        }
    }
}
