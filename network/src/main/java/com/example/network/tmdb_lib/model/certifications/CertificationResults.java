package com.example.network.tmdb_lib.model.certifications;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonProperty;

import com.example.network.tmdb_lib.model.certifications.Certification;
import com.example.network.tmdb_lib.model.core.AbstractJsonMapping;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class CertificationResults extends AbstractJsonMapping {
    @JsonProperty("certifications")
    private Map<String, List<Certification>> certifications = new HashMap<>();
}
