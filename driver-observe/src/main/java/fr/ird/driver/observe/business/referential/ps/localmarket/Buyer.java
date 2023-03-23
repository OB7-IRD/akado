package fr.ird.driver.observe.business.referential.ps.localmarket;

import fr.ird.driver.observe.business.referential.I18nReferentialEntity;
import fr.ird.driver.observe.business.referential.common.Harbour;

import java.util.Objects;
import java.util.function.Supplier;

/**
 * Created on 18/03/2023.
 *
 * @author Tony Chemit - dev@tchemit.fr
 * @since 1.0.0
 */
public class Buyer extends I18nReferentialEntity {

    private String email;
    private String phone;
    private String address;
    private Supplier<Harbour> harbour = () -> null;

    public Harbour getHarbour() {
        return harbour.get();
    }

    public void setHarbour(Supplier<Harbour> harbour) {
        this.harbour = Objects.requireNonNull(harbour);
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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
}
