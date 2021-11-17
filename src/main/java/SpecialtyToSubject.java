import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import com.google.common.collect.Multimap;

import java.util.Collection;

public class SpecialtyToSubject {
    private BiMap<String, String> domainIdToName;
    private Multimap<String, Collection<String>> domainIdToSpecialtyId;
    private BiMap<String, Specialty> specialtyIdToName;

    public SpecialtyToSubject() {
        domainIdToName = HashBiMap.create();
        domainIdToSpecialtyId = ArrayListMultimap.create();
        specialtyIdToName = HashBiMap.create();
    }

    public SpecialtyToSubject(BiMap<String, String> domainIdToName, Multimap<String, Collection<String>> domainIdToSpecialtyId,
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

    public Multimap<String, Collection<String>> getDomainIdToSpecialtyId() {
        return domainIdToSpecialtyId;
    }

    public void setDomainIdToSpecialtyId(Multimap<String, Collection<String>> domainIdToSpecialtyId) {
        this.domainIdToSpecialtyId = domainIdToSpecialtyId;
    }

    public BiMap<String, Specialty> getSpecialtyIdToName() {
        return specialtyIdToName;
    }

    public void printFirst(){
        for(String key : domainIdToName.keySet()){
            System.out.println("Key: " + key + " value: " + domainIdToName.get(key));
        }
    }

    public void printSecond(){
        for(String key : domainIdToSpecialtyId.keySet()){
            System.out.println("Key: " + key + " value: " + domainIdToSpecialtyId.get(key));
        }
    }
    //TODO make sort by key
    public void printThird(){
        for(String key : specialtyIdToName.keySet()){
            System.out.println("Key: " + key + " value: " + specialtyIdToName.get(key));
        }
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
