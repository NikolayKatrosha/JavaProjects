package pl.home;

public class DomainRequest {
    private String domainName;
    private Integer originSecond;
    private Integer shift;
    private String status;

    public String getDomainName() {
        return domainName;
    }

    public void setDomainName(String domainName) {
        this.domainName = domainName;
    }

    public Integer getOriginSecond() {
        return originSecond;
    }

    public void setOriginSecond(Integer originSecond) {
        this.originSecond = originSecond;
    }

    public Integer getShift() {
        return shift;
    }

    public void setShift(Integer shift) {
        this.shift = shift;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getSecond() {
        return originSecond - shift;
    }
}
