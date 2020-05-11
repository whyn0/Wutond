/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proj.map.progettoMap1920.adventure.type;

import java.util.Objects;
import java.util.Set;

/**
 *
 * @author whyno
 */
public class Article {
    private ArticleType type;
    private String name;
    private Set<String> alias;

    public Article(ArticleType type, String name) {
        this.type = type;
        this.name = name;
    }

    public Article(ArticleType type) {
        this.type = type;
    }

    public ArticleType getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    public Set<String> getAlias() {
        return alias;
    }

    public void setType(ArticleType type) {
        this.type = type;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAlias(Set<String> alias) {
        this.alias = alias;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 13 * hash + Objects.hashCode(this.type);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Article other = (Article) obj;
        if (this.type != other.type) {
            return false;
        }
        return true;
    }

    public Article(ArticleType type, String name, Set<String> alias) {
        this.type = type;
        this.name = name;
        this.alias = alias;
    }
    
}
