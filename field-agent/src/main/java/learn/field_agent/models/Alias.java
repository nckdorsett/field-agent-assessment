package learn.field_agent.models;

public class Alias {
    private int aliasId;
    private String aliasName;
    private String persona;
    private int agentId;

    public Alias() {

    }
    public Alias(int aliasId, String aliasName, String persona, int agentId) {
        this.aliasId = aliasId;
        this.aliasName = aliasName;
        this.persona = persona;
        this.agentId = agentId;
    }

    public int getAliasId() {
        return aliasId;
    }

    public void setAliasId(int aliasId) {
        this.aliasId = aliasId;
    }

    public String getAliasName() {
        return aliasName;
    }

    public void setAliasName(String aliasName) {
        this.aliasName = aliasName;
    }

    public String getPersona() {
        return persona;
    }

    public void setPersona(String persona) {
        this.persona = persona;
    }

    public int getAgentId() {
        return agentId;
    }

    public void setAgentId(int agentId) {
        this.agentId = agentId;
    }
}
