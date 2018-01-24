package main.java.erp_crm.backend.model.erp;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.*;
import io.swagger.annotations.ApiModelProperty;
import main.java.erp_crm.backend.model.common.Unit;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.util.Objects;

/**
 * Article
 */
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2017-11-15T00:41:28.115Z")
@Entity
public class Article {
  @JsonProperty("id")
  @Id
  @GeneratedValue
  private Integer id;

  @JsonProperty("availability")
  @NotNull
  @Column(nullable = false, unique = false)
  private Integer availability;

  @JsonProperty("name")
  @NotNull
  @Column(nullable = false, unique = false)
  private String name;

  @JsonProperty("units")
  @NotNull
  @ManyToOne(cascade = CascadeType.PERSIST)
  @JoinColumn(name = "unit_id")
  private Unit unit;

  @JsonProperty("unitPrice")
  @NotNull
  @Column(nullable = false, unique = false)
  private BigDecimal unitPrice;

  @JsonProperty("weight")
  private Float weight = null;

  public Article(){}

  public Article(Integer id, Integer availability, Unit unit, BigDecimal unitPrice) {
    this.id = id;
    this.availability = availability;
    this.unit = unit;
    this.unitPrice = unitPrice;
  }

  public Article(Integer id) {
    this.id = id;
  }

  public Article id(Integer id) {
    this.id = id;
    return this;
  }

   /**
   * Get id
   * @return id
  **/
  @ApiModelProperty(required = true, value = "")
  @NotNull


  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public Article availability(Integer availability) {
    this.availability = availability;
    return this;
  }

   /**
   * Get availability
   * @return availability
  **/
  @ApiModelProperty(required = true, value = "")
  @NotNull


  public Integer getAvailability() {
    return availability;
  }

  public void setAvailability(Integer availability) {
    this.availability = availability;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Article unitId(Unit unitId) {
    this.unit = unitId;
    return this;
  }

   /**
   * Get units
   * @return units
  **/
  @ApiModelProperty(required = true, value = "")
  @NotNull


  public Unit getUnit() {
    return unit;
  }

  public void setUnit(Unit unit) {
    this.unit = unit;
  }

  public Article unitPrice(BigDecimal unitPrice) {
    this.unitPrice = unitPrice;
    return this;
  }

   /**
   * Get unitPrice
   * @return unitPrice
  **/
  @ApiModelProperty(required = true, value = "")
  @NotNull

  @Valid

  public BigDecimal getUnitPrice() {
    return unitPrice;
  }

  public void setUnitPrice(BigDecimal unitPrice) {
    this.unitPrice = unitPrice;
  }

  public Article weight(Float weight) {
    this.weight = weight;
    return this;
  }

   /**
   * Get weight
   * @return weight
  **/
  @ApiModelProperty(value = "")


  public Float getWeight() {
    return weight;
  }

  public void setWeight(Float weight) {
    this.weight = weight;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Article article = (Article) o;
    return Objects.equals(this.id, article.id) &&
        Objects.equals(this.availability, article.availability) &&
        Objects.equals(this.unit, article.unit) &&
        Objects.equals(this.unitPrice, article.unitPrice) &&
        Objects.equals(this.weight, article.weight);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, availability, unit, unitPrice, weight);
  }

  @Override
  public String toString() {
    return name;
    /*StringBuilder sb = new StringBuilder();
    sb.append("class Article {\n");

    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    availability: ").append(toIndentedString(availability)).append("\n");
    sb.append("    units: ").append(toIndentedString(units)).append("\n");
    sb.append("    unitPrice: ").append(toIndentedString(unitPrice)).append("\n");
    sb.append("    weight: ").append(toIndentedString(weight)).append("\n");
    sb.append("}");
    return sb.toString();*/
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces
   * (except the first line).
   */
  private String toIndentedString(Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }
  public String serialize(){
    final GsonBuilder gsonBuilder = new GsonBuilder();
    gsonBuilder.registerTypeAdapter(Article.class, new ArticleSerializer());
    final Gson gson = gsonBuilder.create();
    return gson.toJson(this);
  }

  class ArticleSerializer implements JsonSerializer<Article> {

    @Override
    public JsonElement serialize(final Article article, final Type typeOfSrc, final JsonSerializationContext context) {
      JsonObject object = new JsonObject();
      object.addProperty("id", id);
      object.addProperty("availability", availability);
      object.addProperty("name", name);
      object.addProperty("unitPrice", unitPrice);
      object.addProperty("weight", weight);
      object.addProperty("unit", unit.getId());
      return object;
    }
  }
}

