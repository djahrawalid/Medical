package com.example.medical.model;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.example.medical.background.DateConverter;

import java.io.Serializable;
import java.util.Date;

/**
 * @Entity  : hna beh tgolo hed class c est un table dans la base de données
 * @TypeConverters  : sqlite ma yssuportich les dates, mala lezm tmdlo class converter beh y3ref kifeh ysauvgarder la dat fel bd
 * @Serializable : han beh t9der object te3 product men fel intent
 */
@Entity
@TypeConverters(DateConverter.class)
public class Product implements Serializable {
    @PrimaryKey
    private long id;
    private String className,barCode,nomComercial,laboratoire,denomination,formePharma,
        lot,description;

    private boolean isSync;
    private int dureeConservation,quantity;
    private double price;
    private Date dateFabrication,datePeremption;
    private boolean isRemborsable;
    /** Ignore m3naha ki yjib les données men sqlite w yhothm fel object ma ykhdmch bel constructeur */
    @Ignore
    public Product() {
    }

    public Product(String className ,String barCode, String nomComercial, String laboratoire, String denomination,
                   String formePharma, String lot, String description, int dureeConservation, int quantity,
                   double price, Date dateFabrication, Date datePeremption, boolean isRemborsable,boolean isSync) {
        this.className = className;
        this.barCode = barCode;
        this.nomComercial = nomComercial;
        this.laboratoire = laboratoire;
        this.denomination = denomination;
        this.formePharma = formePharma;
        this.lot = lot;
        this.description = description;
        this.dureeConservation = dureeConservation;
        this.quantity = quantity;
        this.price = price;
        this.dateFabrication = dateFabrication;
        this.datePeremption = datePeremption;
        this.isRemborsable = isRemborsable;
        this.isSync=isSync;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getBarCode() {
        return barCode;
    }

    public void setBarCode(String barCode) {
        this.barCode = barCode;
    }

    public String getNomComercial() {
        return nomComercial;
    }

    public boolean isSync() {
        return isSync;
    }

    public void setSync(boolean sync) {
        isSync = sync;
    }

    public void setNomComercial(String nomComercial) {
        this.nomComercial = nomComercial;
    }

    public String getLaboratoire() {
        return laboratoire;
    }

    public void setLaboratoire(String laboratoire) {
        this.laboratoire = laboratoire;
    }

    public String getDenomination() {
        return denomination;
    }

    public void setDenomination(String denomination) {
        this.denomination = denomination;
    }

    public String getFormePharma() {
        return formePharma;
    }

    public void setFormePharma(String formePharma) {
        this.formePharma = formePharma;
    }

    public String getLot() {
        return lot;
    }

    public void setLot(String lot) {
        this.lot = lot;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getDureeConservation() {
        return dureeConservation;
    }

    public void setDureeConservation(int dureeConservation) {
        this.dureeConservation = dureeConservation;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Date getDateFabrication() {
        return dateFabrication;
    }

    public void setDateFabrication(Date dateFabrication) {
        this.dateFabrication = dateFabrication;
    }

    public Date getDatePeremption() {
        return datePeremption;
    }

    public void setDatePeremption(Date datePeremption) {
        this.datePeremption = datePeremption;
    }

    public boolean isRemborsable() {
        return isRemborsable;
    }

    public void setRemborsable(boolean remborsable) {
        isRemborsable = remborsable;
    }
}
