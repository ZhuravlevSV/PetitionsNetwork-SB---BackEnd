package zhurasem.project.domain;

/**
 * Common supertype for domain types.
 * @param <ID> primary key type
 */
public interface DomainEntity<ID> {
    /**
     *
     * @return the primary key value of this instance
     */
    ID getId();
}
