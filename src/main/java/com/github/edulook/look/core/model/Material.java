package com.github.edulook.look.core.model;

import com.github.edulook.look.core.data.Option;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Optional;

@Entity
@Builder
@Getter
@Setter
@NoArgsConstructor
public class Material {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer internalId;

    private String id;

    private String name;

    private String description;

    private String type;

    private String originLink;

    private String previewLink;
    @OneToOne
    private PageContent content;
    @Embedded
    private Option option;
    @ManyToOne
    private WorkMaterial workMaterial;

    public Optional<PageContent> getContent() {
        return Optional.ofNullable(content);
    }


    public Optional<Option> getOption() {
        return Optional.ofNullable(option);
    }

    public Material(Integer internalId, String id,
                    String name, String description, String type, String originLink,
                    String previewLink, PageContent content, Option option,
                    WorkMaterial workMaterial) {
        this.internalId = internalId;
        this.id = id;
        this.name = name;
        this.description = description;
        this.type = type;
        this.originLink = originLink;
        this.previewLink = previewLink;
        this.content = content;
        this.option = Option.withDefaults();
        this.workMaterial = workMaterial;
    }
}
