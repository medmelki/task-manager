package com.taskmanager.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "user")
public class User implements Serializable, UserDetails {

    @Id
    private String username;
    private String firstname;
    private String lastname;
    private String password;
    private String role;
    private String companyId;
    private String phone;
    private String address;
    private String email;
    private String manager;
    @Lob
    private byte[] pic;
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "user")
    Set<Picture> pictures = new LinkedHashSet<>();
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "user")
    Set<Document> documents;
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "manager")
    Set<Task> tasksToManage;
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "manager")
    Set<Node> nodesToManage;
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "manager")
    Set<Pack> packsToManage;
    @OneToOne
    @JoinColumn(name = "gps_fk", nullable = true)
    private GPS gps;
    @ManyToMany(fetch = FetchType.EAGER, mappedBy = "users")
    private List<Task> tasks;
    private String deviceId;
    private String ServerIP;


    public User() {
    }

    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Set<GrantedAuthority> authList = new HashSet<GrantedAuthority>();

        authList.add(new SimpleGrantedAuthority(this.getRole()));
        return authList;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Set<Picture> getPictures() {
        return pictures;
    }

    public void setPictures(Set<Picture> pictures) {
        this.pictures = pictures;
    }

    public Set<Document> getDocuments() {
        return documents;
    }

    public void setDocuments(Set<Document> documents) {
        this.documents = documents;
    }

    public GPS getGps() {
        return gps;
    }

    public void setGps(GPS gps) {
        this.gps = gps;
    }

    public byte[] getPic() {
        return pic;
    }

    public void setPic(byte[] pic) {
        this.pic = pic;
    }

    @JsonIgnore
    public List<Task> getTasks() {
        return tasks;
    }

    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getServerIP() {
        return ServerIP;
    }

    public void setServerIP(String serverIP) {
        ServerIP = serverIP;
    }

    public Set<Task> getTasksToManage() {
        return tasksToManage;
    }

    public void setTasksToManage(Set<Task> tasksToManage) {
        this.tasksToManage = tasksToManage;
    }

    public Set<Node> getNodesToManage() {
        return nodesToManage;
    }

    public void setNodesToManage(Set<Node> nodesToManage) {
        this.nodesToManage = nodesToManage;
    }

    public Set<Pack> getPacksToManage() {
        return packsToManage;
    }

    public void setPacksToManage(Set<Pack> packsToManage) {
        this.packsToManage = packsToManage;
    }

    public String getManager() {
        return manager;
    }

    public void setManager(String manager) {
        this.manager = manager;
    }
}
