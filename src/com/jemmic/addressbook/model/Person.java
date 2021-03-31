package com.jemmic.addressbook.model;


/**
 * Person Model to handle the data of contacts, including mandatory and optional fields.
 */
public class Person {

    private String name;
    private String surname;
    private String phone;
    private String email;
    private String age;
    private String hairColor;
    private String categoryName;
    private String relationship;
    private String friendshipYear;


    public Person() {
    }

    public Person(String name, String surname, String phone, String email, String age, String hairColor, String categoryName, String relationship, String friendshipYear) {
        this.name = name;
        this.surname = surname;
        this.phone = phone;
        this.email = email;
        this.age = age;
        this.hairColor = hairColor;
        this.categoryName = categoryName;
        this.relationship = relationship;
        this.friendshipYear = friendshipYear;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getHairColor() {
        return hairColor;
    }

    public void setHairColor(String hairColor) {
        this.hairColor = hairColor;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getRelationship() {
        return relationship;
    }

    public void setRelationship(String relationship) {
        this.relationship = relationship;
    }

    public String getFriendshipYear() {
        return friendshipYear;
    }

    public void setFriendshipYear(String friendshipYear) {
        this.friendshipYear = friendshipYear;
    }

    @Override
    public String toString() {
        return "Person{" +
                "  name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", phone='" + phone + '\'' +
                ", email='" + email + '\'' +
                ", age='" + age + '\'' +
                ", hairColor='" + hairColor + '\'' +
                ", categoryName='" + categoryName + '\'' +
                ", relationship='" + relationship + '\'' +
                ", friendshipYear='" + friendshipYear + '\'' +
                '}';
    }

}
