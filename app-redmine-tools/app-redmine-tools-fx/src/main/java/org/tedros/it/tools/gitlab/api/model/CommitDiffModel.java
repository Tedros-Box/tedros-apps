package org.tedros.it.tools.gitlab.api.model;

import com.google.gson.annotations.SerializedName;

/**
 * Representa a diferença (diff) de um arquivo específico em um commit da API do GitLab.
 */
public record CommitDiffModel(
    @SerializedName("diff") String diff,
    @SerializedName("new_path") String newPath,
    @SerializedName("old_path") String oldPath,
    @SerializedName("a_mode") String aMode,
    @SerializedName("b_mode") String bMode,
    @SerializedName("new_file") boolean newFile,
    @SerializedName("renamed_file") boolean renamedFile,
    @SerializedName("deleted_file") boolean deletedFile,
    @SerializedName("generated_file") Object generatedFile // Pode ser null ou outro tipo no futuro, Object é seguro
) {
}
