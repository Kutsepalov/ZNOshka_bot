package com.softserve.bot.service.parser;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import com.softserve.bot.model.Specialty;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class SpecialtyToSubject {
    private BiMap<String, String> domainIdToName;
    private Map<String, Collection<String>> domainIdToSpecialtyId;
    private BiMap<String, Specialty> specialtyIdToName;

    public SpecialtyToSubject() {
        domainIdToName = HashBiMap.create();
        domainIdToSpecialtyId = new HashMap<>();
        specialtyIdToName = HashBiMap.create();
    }

    public SpecialtyToSubject(BiMap<String, String> domainIdToName, Map<String, Collection<String>> domainIdToSpecialtyId,
                              BiMap<String, Specialty> specialtyIdToName) {
        this.domainIdToName = domainIdToName;
        this.domainIdToSpecialtyId = domainIdToSpecialtyId;
        this.specialtyIdToName = specialtyIdToName;
    }

    public BiMap<String, String> getDomainIdToName() {
        return domainIdToName;
    }

    public void setDomainIdToName(BiMap<String, String> domainIdToName) {
        this.domainIdToName = domainIdToName;
    }

    public Map<String, Collection<String>> getDomainIdToSpecialtyId() {
        return domainIdToSpecialtyId;
    }

    public void setDomainIdToSpecialtyId(Map<String, Collection<String>> domainIdToSpecialtyId) {
        this.domainIdToSpecialtyId = domainIdToSpecialtyId;
    }

    public BiMap<String, Specialty> getSpecialtyIdToName() {
        return specialtyIdToName;
    }

    public void setSpecialtyIdToName(BiMap<String, Specialty> specialtyIdToName) {
        this.specialtyIdToName = specialtyIdToName;
    }

    @Override
    public String toString() {
        return "SpecialtyToSubject{" +
                "domainIdToName=" + domainIdToName +
                ", domainIdToSpecialtyId=" + domainIdToSpecialtyId +
                ", specialtyIdToName=" + specialtyIdToName +
                '}';
    }
}
