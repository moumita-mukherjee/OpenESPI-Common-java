package org.energyos.espi.common.domain;

import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlTransient;

@XmlAccessorType(XmlAccessType.FIELD)
@Embeddable
@Cacheable(true)
public class ApplicationInformationScope {

	@Column(name = "scope")
	private String scope;
	
	@XmlTransient
	@Column(name = "description")
	private String description;
	
	@XmlTransient
	@Transient
	private boolean selected;

	public boolean isSelected() {
		return selected;
	}

	public void setSelected(boolean selected) {
		this.selected = selected;
	}

	public ApplicationInformationScope() {
	}

	public ApplicationInformationScope(String s) {
		scope = s;
		description=s;
	}

	public String getScope() {
		return scope;
	}

	public void setScope(String scope) {
		this.scope = scope;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}


	@Override
	public boolean equals(java.lang.Object o) {
		
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;

		ApplicationInformationScope ap = (ApplicationInformationScope) o;

		if (scope != null ? !scope.equals(ap.scope)
				: ap.scope != null)
			return false;

		return true;
	}
    @Override
    public int hashCode() {
        int result = scope != null ? scope.hashCode() : 0;
        return result;

    }

	public String toString() {

		return description;
	}
}
