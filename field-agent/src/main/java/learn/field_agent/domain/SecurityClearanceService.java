package learn.field_agent.domain;

import learn.field_agent.data.AgencyAgentJdbcTemplateRepository;
import learn.field_agent.data.AgencyAgentRepository;
import learn.field_agent.data.SecurityClearanceJdbcTemplateRepository;
import learn.field_agent.data.SecurityClearanceRepository;
import learn.field_agent.models.AgencyAgent;
import learn.field_agent.models.SecurityClearance;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SecurityClearanceService {

    private final SecurityClearanceRepository securityClearanceRepository;
    private final AgencyAgentRepository agencyAgentRepository;

    public SecurityClearanceService(SecurityClearanceJdbcTemplateRepository securityClearanceRepository, AgencyAgentJdbcTemplateRepository agencyAgentRepository)
    {
        this.securityClearanceRepository = securityClearanceRepository;
        this.agencyAgentRepository = agencyAgentRepository;
    }

    public List<SecurityClearance> findAll() {
        return securityClearanceRepository.findAll();
    }

    public SecurityClearance findById(int securityClearanceId) {
        return securityClearanceRepository.findById(securityClearanceId);
    }

    public SecurityClearance findByName(String securityClearanceName) {
        return securityClearanceRepository.findByName(securityClearanceName);
    }

    public Result<SecurityClearance> add(SecurityClearance securityClearance) {
        Result<SecurityClearance> result = validate(securityClearance);
        if (!result.isSuccess()) {
            return result;
        }

        if (securityClearance.getSecurityClearanceId() != 0) {
            result.addMessage("Security Clearance ID must be 0 while adding.", ResultType.INVALID);
            return result;
        }

        result.setPayload(securityClearanceRepository.add(securityClearance));
        return result;
    }

    public Result<SecurityClearance> update(SecurityClearance securityClearance) {
        Result<SecurityClearance> result = validate(securityClearance);
        if (!result.isSuccess()) {
            return result;
        }

        if (securityClearance.getSecurityClearanceId() <= 0) {
            result.addMessage("Security Clearance ID must be set for `update` function.", ResultType.INVALID);
            return result;
        }

        if (!securityClearanceRepository.update(securityClearance)) {
            result.addMessage("Security Clearance ID not found.", ResultType.NOT_FOUND);
            return result;
        }

        result.setPayload(securityClearance);
        return result;
    }

    public Result<SecurityClearance> deleteById(int securityClearanceId) {
        Result<SecurityClearance> result = validateDelete(securityClearanceId);
        if (!result.isSuccess()) {
            return result;
        }

        if (!securityClearanceRepository.delete(securityClearanceId)) {
            result.addMessage("Security Clearance ID not found.", ResultType.NOT_FOUND);
            return result;
        }

        return result;
    }

    private Result<SecurityClearance> validate(SecurityClearance securityClearance) {
        Result<SecurityClearance> result = new Result<>();
        if(securityClearance == null) {
            result.addMessage("Security Clearance must exist.", ResultType.INVALID);
            return result;
        }

        if(Validations.isNullOrBlank(securityClearance.getName())) {
            result.addMessage("Name cannot be empty", ResultType.INVALID);
            return result;
        }

        for (SecurityClearance sc : findAll()) {
            if (sc.getName().equalsIgnoreCase(securityClearance.getName())) {
                result.addMessage("Name cannot be duplicated", ResultType.INVALID);
                return result;
            }
        }
        return result;
    }

    private Result<SecurityClearance> validateDelete(int securityClearanceId) {
        Result<SecurityClearance> result = new Result<>();
        for (AgencyAgent aa : agencyAgentRepository.findAll()) {
            if (aa.getSecurityClearance().getSecurityClearanceId() == securityClearanceId) {
                result.addMessage("Cannot delete a Security Clearance that's being used.", ResultType.INVALID);
                return result;
            }
        }
        return result;
    }
}
