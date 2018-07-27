package mappers;

import org.apache.ibatis.annotations.Param;

/**
 * StorageMapper. Created at 7/27/2018 11:36 AM by @author Timur Isachenko
 * @isatimur | † be patient and test it! †
 */
public interface StorageMapper {
    Object selectObject(
            @Param("someField") int someField,
            @Param("secondField") String secondField);

    void updateObject(Object o);
}
