package spring6.bynaus.nohs.domain;

import java.util.UUID;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Post{
    
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @JdbcTypeCode(SqlTypes.CHAR)
    @Column(length = 36, columnDefinition = "varchar(36)", updatable = false, nullable = false)
    private UUID id;

    private String origContent;

    // @ManyToOne(cascade = CascadeType.PERSIST)
    // @JoinColumn(name = "author_id")
    // private Person author;
    
    @JdbcTypeCode(SqlTypes.BOOLEAN)
    private boolean isHateSpeech;
    private String redactedContent;
    private String justification;
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
    // public Person getAuthor() {
    //     return author;
    // }
    // public void setAuthor(Person author) {
    //     this.author = author;
    // }
    public boolean getIsHateSpeech() {
        return isHateSpeech;
    }
    public void setIsHateSpeech(boolean isHateSpeech) {
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
}