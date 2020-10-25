package learn.field_agent.data;

import learn.field_agent.models.SecurityClearance;

import java.util.List;

public interface SecurityClearanceRepository {

    List<SecurityClearance> findAll();

    SecurityClearance findById(int securityClearanceId);

    SecurityClearance findByName(String securityClearanceName);

    SecurityClearance add(SecurityClearance securityClearance);

    boolean update(SecurityClearance securityClearance);

    boolean delete(int securityClearanceId);
}
