package learn.field_agent.data;

import learn.field_agent.models.AgencyAgent;
import learn.field_agent.data.mappers.AgencyAgentMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class AgencyAgentJdbcTemplateRepository implements AgencyAgentRepository {

    private final JdbcTemplate jdbcTemplate;

    public AgencyAgentJdbcTemplateRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<AgencyAgent> findAll() {
        final String sql = "select aa.agency_id, aa.agent_id, aa.identifier, aa.activation_date, aa.is_active, " +
                "s.security_clearance_id, s.name security_clearance_name," +
                "a.agent_id, a.first_name, a.middle_name, a.last_name, a.dob, a.height_in_inches " +
                "from agency_agent aa " +
                "inner join agent a on a.agent_id = aa.agency_id " +
                "inner join security_clearance s on s.security_clearance_id = aa.security_clearance_id;";

        return new ArrayList<AgencyAgent>(jdbcTemplate.query(sql, new AgencyAgentMapper()));
    }

    @Override
    public boolean add(AgencyAgent agencyAgent) {

        final String sql = "insert into agency_agent (agency_id, agent_id, identifier, security_clearance_id, "
                + "activation_date, is_active) values "
                + "(?,?,?,?,?,?);";

        return jdbcTemplate.update(sql,
                agencyAgent.getAgencyId(),
                agencyAgent.getAgent().getAgentId(),
                agencyAgent.getIdentifier(),
                agencyAgent.getSecurityClearance().getSecurityClearanceId(),
                agencyAgent.getActivationDate(),
                agencyAgent.isActive()) > 0;
    }

    @Override
    public boolean update(AgencyAgent agencyAgent) {

        final String sql = "update agency_agent set "
                + "identifier = ?, "
                + "security_clearance_id = ?, "
                + "activation_date = ?, "
                + "is_active = ? "
                + "where agency_id = ? and agent_id = ?;";

        return jdbcTemplate.update(sql,
                agencyAgent.getIdentifier(),
                agencyAgent.getSecurityClearance().getSecurityClearanceId(),
                agencyAgent.getActivationDate(),
                agencyAgent.isActive(),
                agencyAgent.getAgencyId(),
                agencyAgent.getAgent().getAgentId()) > 0;

    }

    @Override
    public boolean deleteByKey(int agencyId, int agentId) {

        final String sql = "delete from agency_agent "
                + "where agency_id = ? and agent_id = ?;";

        return jdbcTemplate.update(sql, agencyId, agentId) > 0;
    }
}
