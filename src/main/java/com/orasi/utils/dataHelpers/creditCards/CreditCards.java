package com.orasi.utils.dataHelpers.creditCards;

import com.orasi.exception.AutomationException;
import com.orasi.utils.dataHelpers.personFactory.Address;
import com.orasi.utils.dataHelpers.personFactory.Person;

/**
 * Container class to store credit cards and easily retrieve them
 * Test are considered test cards for PayPal as listed below, but card
 * number info still not plaintext to encourage data masking
 *
 * @author justin.phlegar@orasi.com
 * @see https://www.paypalobjects.com/en_US/vhelp/paypalmanager_help/credit_card_numbers.htm
 *
 */
public class CreditCards {
    private static ThreadLocal<Person> person = new ThreadLocal<>();
    private static ThreadLocal<Address> address = new ThreadLocal<>();

    private static Person getPerson() {
        if (person.get() == null) {
            person.set(new Person());
            address.set(person.get().primaryAddress());
        }

        return person.get();
    }

    /**
     * Allow tester to reuse a pre-defined and associate that persons address info to the card
     * Will check for a "Billing" type of address exists. If Billing exists, associate that address
     * to card. If Billing does NOT exist, then use the persons primary address
     *
     * This is done for cases where apps may have business rules that billing address info entered must
     * match records on file. By using the Person factory, that make this easier to do.
     *
     * @param person
     */
    public CreditCards(Person person) {
        CreditCards.person.set(person);
        boolean billingFound = false;
        for (Address personAddress : person.getAllAddresses()) {
            if ("Billing".equalsIgnoreCase(personAddress.getType())) {
                address.set(personAddress);
                billingFound = true;
            }
        }

        if (!billingFound) {
            address.set(person.primaryAddress());
        }
    }

    /*
     * Expected storage pattern is as follows
     * return new CreditCard(
     * cardType,
     * nameOnCard,
     * encodedCardNumber,
     * encodedSecurityCode,
     * expirationMonth,
     * expirationYear,
     * billingAddress,
     * billingAddress2,
     * billingCity,
     * billingState,
     * billingCountry,
     * billingZipCode
     * );
     */

    public final static CreditCard AMEX() {
        return new CreditCard(
                "AMEX",
                getPerson().getFullName(),
                "Mzc4MjgyMjQ2MzEwMDA1",
                "MzI2NQ==",
                "12",
                "20",
                address.get().getAddress1(),
                address.get().getAddress2(),
                address.get().getCity(),
                address.get().getStateAbbv(),
                address.get().getCountryAbbv(),
                address.get().getZipCode());
    }

    public final static CreditCard DINERSCLUB() {
        return new CreditCard(
                "DINERSCLUB",
                getPerson().getFullName(),
                "MzA1NjkzMDkwMjU5MDQ=",
                "NzE4",
                "12",
                "20",
                address.get().getAddress1(),
                address.get().getAddress2(),
                address.get().getCity(),
                address.get().getStateAbbv(),
                address.get().getCountryAbbv(),
                address.get().getZipCode());
    }

    public final static CreditCard DISCOVER() {
        return new CreditCard(
                "DISCOVER",
                getPerson().getFullName(),
                "NjAxMTExMTExMTExMTExNw==",
                "NzE4",
                "12",
                "20",
                address.get().getAddress1(),
                address.get().getAddress2(),
                address.get().getCity(),
                address.get().getStateAbbv(),
                address.get().getCountryAbbv(),
                address.get().getZipCode());
    }

    public final static CreditCard JCB() {
        return new CreditCard(
                "JCB",
                getPerson().getFullName(),
                "MzUzMDExMTMzMzMwMDAwMA==",
                "MDM2",
                "12",
                "20",
                address.get().getAddress1(),
                address.get().getAddress2(),
                address.get().getCity(),
                address.get().getStateAbbv(),
                address.get().getCountryAbbv(),
                address.get().getZipCode());
    }

    public final static CreditCard MASTERCARD() {
        return new CreditCard(
                "MASTERCARD",
                getPerson().getFullName(),
                "NTEwNTEwNTEwNTEwNTEwMA==",
                "MDM2",
                "12",
                "20",
                address.get().getAddress1(),
                address.get().getAddress2(),
                address.get().getCity(),
                address.get().getStateAbbv(),
                address.get().getCountryAbbv(),
                address.get().getZipCode());
    }

    public final static CreditCard VISA() {
        return new CreditCard(
                "VISA",
                getPerson().getFullName(),
                "NDAxMjg4ODg4ODg4MTg4MQ==",
                "OTgw",
                "12",
                "20",
                address.get().getAddress1(),
                address.get().getAddress2(),
                address.get().getCity(),
                address.get().getStateAbbv(),
                address.get().getCountryAbbv(),
                address.get().getZipCode());
    }

    public final static CreditCard VISA_EXPIRED() {
        return new CreditCard(
                "VISA_EXPIRED",
                getPerson().getFullName(),
                "NDAxMjg4ODg4ODg4MTg4MQ==",
                "OTgw",
                "12",
                "12",
                address.get().getAddress1(),
                address.get().getAddress2(),
                address.get().getCity(),
                address.get().getStateAbbv(),
                address.get().getCountryAbbv(),
                address.get().getZipCode());
    }

    /**
     * This method allows you to enter the type of card you want
     *
     * @param type
     * @return
     */
    public static CreditCard getCreditCardByType(String type) {
        switch (type.toLowerCase().replaceAll("\\s+|_", "")) {

            case "amex":
            case "americanexpress":
                return AMEX();

            case "diners":
            case "dinersclub":
                return DINERSCLUB();

            case "disc":
            case "discover":
                return DISCOVER();

            case "jcb":
                return JCB();

            case "mc":
            case "mastercard":
                return MASTERCARD();

            case "visa":
                return VISA();

            case "visaexpired":
                return VISA_EXPIRED();

            /*
             * case "gc":
             * case "gift":
             * case "giftcard":
             * // return GIFTCARD(); for later storage
             * break;
             */

        }

        // Should not be at this point unless card was not found above
        throw new AutomationException("Credit Card type of [ " + type + " ] was not valid or availible");
    }
}
