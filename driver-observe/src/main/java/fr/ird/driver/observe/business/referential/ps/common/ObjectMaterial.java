package fr.ird.driver.observe.business.referential.ps.common;

import fr.ird.driver.observe.business.referential.I18nReferentialEntity;

import java.util.Objects;
import java.util.function.Supplier;

/**
 * Created on 18/03/2023.
 *
 * @author Tony Chemit - dev@tchemit.fr
 * @since 1.0.0
 */
public class ObjectMaterial extends I18nReferentialEntity {

    private String legacyCode;
    private String standardCode;
    private Boolean biodegradable;
    private Boolean nonEntangling;
    private boolean childrenMultiSelectable;
    private boolean childSelectionMandatory;
    private String validation;
    private Supplier<ObjectMaterial> parent = () -> null;
    private Supplier<ObjectMaterialType> objectMaterialType = () -> null;

    public String getLegacyCode() {
        return legacyCode;
    }

    public void setLegacyCode(String legacyCode) {
        this.legacyCode = legacyCode;
    }

    public String getStandardCode() {
        return standardCode;
    }

    public void setStandardCode(String standardCode) {
        this.standardCode = standardCode;
    }

    public Boolean getBiodegradable() {
        return biodegradable;
    }

    public void setBiodegradable(Boolean biodegradable) {
        this.biodegradable = biodegradable;
    }

    public Boolean getNonEntangling() {
        return nonEntangling;
    }

    public void setNonEntangling(Boolean nonEntangling) {
        this.nonEntangling = nonEntangling;
    }

    public boolean isChildrenMultiSelectable() {
        return childrenMultiSelectable;
    }

    public void setChildrenMultiSelectable(boolean childrenMultiSelectable) {
        this.childrenMultiSelectable = childrenMultiSelectable;
    }

    public boolean isChildSelectionMandatory() {
        return childSelectionMandatory;
    }

    public void setChildSelectionMandatory(boolean childSelectionMandatory) {
        this.childSelectionMandatory = childSelectionMandatory;
    }

    public ObjectMaterial getParent() {
        return parent.get();
    }

    public void setParent(Supplier<ObjectMaterial> parent) {
        this.parent = parent;
    }

    public ObjectMaterialType getObjectMaterialType() {
        return objectMaterialType.get();
    }

    public void setObjectMaterialType(Supplier<ObjectMaterialType> objectMaterialType) {
        this.objectMaterialType = Objects.requireNonNull(objectMaterialType);
    }

    public String getValidation() {
        return validation;
    }

    public void setValidation(String validation) {
        this.validation = validation;
    }
}
