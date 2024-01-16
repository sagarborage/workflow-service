package com.sowermate.user.repositories;

import com.sowermate.user.entities.ConfirmationCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * <h1>ConfirmationCodeRepository Interface</h1>
 * This interface extends {@link JpaRepository} and provides custom query methods for the {@link ConfirmationCode} entity.
 * It serves as the Data Access Object (DAO) in the application for all database interactions related to {@link ConfirmationCode} instances.
 *
 * @author ajadhav
 * @version 1.0
 * @see JpaRepository
 * @see ConfirmationCode
 * @since 2023-12-15
 */
@Repository
public interface ConfirmationCodeRepository extends JpaRepository<ConfirmationCode, Long> {

    /**
     * Finds a confirmation code document by its UUID and associated confirmation code ID.
     *
     * @param uuid   The UUID of the confirmation code document to find.
     * @param userId The ID of the confirmation code associated with the document.
     * @return The confirmation code document matching the provided UUID and confirmation code ID, or null if not found.
     */
    ConfirmationCode findConfirmationCodeByUuidAndUserId(String uuid, Long userId);

    /**
     * Finds all confirmation code documents associated with a specific confirmation code.
     *
     * @param userId The ID of the confirmation code for whom to retrieve documents.
     * @return A list of confirmation code documents associated with the specified confirmation code.
     */
    List<ConfirmationCode> findAllByUserId(Long userId);

    /**
     * Finds all confirmation code documents associated with a specific confirmation code and with the given activity status.
     *
     * @param userId   The ID of the confirmation code for whom to retrieve documents.
     * @param isUsed The activity status of the documents to retrieve.
     * @return A list of confirmation code documents associated with the specified confirmation code and activity status.
     */
    List<ConfirmationCode> findAllByUserIdAndIsUsed(Long userId, Boolean isUsed);

    /**
     * Finds the ID of a confirmation code document based on its UUID.
     *
     * @param uuid The UUID of the confirmation code document for which to retrieve the ID.
     * @return An optional containing the ID of the confirmation code document, or empty if not found.
     */
    @Query("select cc.id from ConfirmationCode cc where cc.uuid = :uuid")
    Optional<Long> findIdByUuid(@Param("uuid") String uuid);

    /**
     * Retrieves a confirmation code object based on the provided code.
     *
     * @param code The unique code associated with the confirmation code.
     * @return An {@link Optional} containing the {@link ConfirmationCode} if found,
     */
    Optional<ConfirmationCode> findConfirmationCodeByCode(String code);

    /**
     * Retrieves a confirmation code object based on the provided code.
     *
     * @param userId The unique code associated with the confirmation code.
     * @return An {@link Optional} containing the {@link ConfirmationCode} if found,
     */
    Optional<ConfirmationCode> findConfirmationCodeByUserId(Long userId);

    Optional<ConfirmationCode> findByCodeAndIsUsedIsFalse(String enteredOtp);
}
