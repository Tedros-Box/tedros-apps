package org.tedros.redminetools.git.model;

import com.google.gson.annotations.SerializedName;

public record MergeRequestReferences(
	    @SerializedName("short") String shortReference,
	    String relative,
	    String full
	) {}