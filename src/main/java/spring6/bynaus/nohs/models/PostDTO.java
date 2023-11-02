package spring6.bynaus.nohs.models;

import java.util.UUID;

import lombok.Builder;
import lombok.extern.slf4j.Slf4j;

// @Slf4j
@Builder
public class PostDTO {
    private UUID id;
    private String origContent;
    private PersonDTO author;
    private boolean isHateSpeech;
    private String redactedContent;
    private String justification;
    public PostDTO(UUID id, String origContent, PersonDTO author, boolean isHateSpeech, String redactedContent,
            String justification) {
        this.id = id;
        this.origContent = origContent;
        this.author = author;
        this.isHateSpeech = isHateSpeech;
        this.redactedContent = redactedContent;
        this.justification = justification;
    }
    public PostDTO() {
    }
    // public static org.slf4j.Logger getLog() {
    //     return log;
    // }
    // public static void setLog(org.slf4j.Logger log) {
    //     PostDTO.log = log;
    // }
    public UUID getId() {
        return id;
    }
    public void setId(UUID id) {
        this.id = id;
    }
    public String getOrigContent() {
        return origContent;
    }
    public void setOrigContent(String origContent) {
        this.origContent = origContent;
    }
    public PersonDTO getAuthor() {
        return author;
    }
    public void setAuthor(PersonDTO author) {
        this.author = author;
    }
    public boolean isHateSpeech() {
        return isHateSpeech;
    }
    public void setHateSpeech(boolean isHateSpeech) {
        this.isHateSpeech = isHateSpeech;
    }
    public String getRedactedContent() {
        return redactedContent;
    }
    public void setRedactedContent(String redactedContent) {
        this.redactedContent = redactedContent;
    }
    public String getJustification() {
        return justification;
    }
    public void setJustification(String justification) {
        this.justification = justification;
    }
    @Override
    public String toString() {
        return "Post [id=" + id + ", origContent=" + origContent + ", author=" + author + ", isHateSpeech="
                + isHateSpeech + ", redactedContent=" + redactedContent + ", justification=" + justification + "]";
    }
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        PostDTO other = (PostDTO) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        if (origContent == null) {
            if (other.origContent != null)
                return false;
        } else if (!origContent.equals(other.origContent))
            return false;
        if (author == null) {
            if (other.author != null)
                return false;
        } else if (!author.equals(other.author))
            return false;
        if (isHateSpeech != other.isHateSpeech)
            return false;
        if (redactedContent == null) {
            if (other.redactedContent != null)
                return false;
        } else if (!redactedContent.equals(other.redactedContent))
            return false;
        if (justification == null) {
            if (other.justification != null)
                return false;
        } else if (!justification.equals(other.justification))
            return false;
        return true;
    }
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((origContent == null) ? 0 : origContent.hashCode());
        result = prime * result + ((author == null) ? 0 : author.hashCode());
        result = prime * result + (isHateSpeech ? 1231 : 1237);
        result = prime * result + ((redactedContent == null) ? 0 : redactedContent.hashCode());
        result = prime * result + ((justification == null) ? 0 : justification.hashCode());
        return result;
    }
}
