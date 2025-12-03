package org.tedros.it.tools.gitlab.model;

import com.google.gson.annotations.SerializedName;

public record MergeRequestReferences(
	    @SerializedName("short") String shortReference,
	    String relative,
	    String full
	) {}