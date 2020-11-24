package com.boost.SocialCocktailJavaServer.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.OneToMany;

@Entity
public class Bartender extends User {
    @JsonView(JacksonView.freeContext.class)
    private boolean verified;

    @JsonView(JacksonView.freeContext.class)
    private String barName;
    @JsonView(JacksonView.freeContext.class)
    private String supervisorName;
    @JsonView(JacksonView.freeContext.class)
    private String supervisorPhone;
    
    
    @OneToMany
    @JsonIgnore
    private List<Tip> tips;

    public boolean isVerified() {
        return verified;
    }

    public void setVerified(boolean verified) {
        this.verified = verified;
    }

    public String getBarName() {
        return barName;
    }

    public void setBarName(String barName) {
        this.barName = barName;
    }

    public String getSupervisorName() {
        return supervisorName;
    }

    public void setSupervisorName(String supervisorName) {
        this.supervisorName = supervisorName;
    }

    public String getSupervisorPhone() {
        return supervisorPhone;
    }

    public void setSupervisorPhone(String supervisorPhone) {
        this.supervisorPhone = supervisorPhone;
    }

	public List<Tip> getTips() {
		return tips;
	}

	public void setTips(List<Tip> tips) {
		this.tips = tips;
	}
}
