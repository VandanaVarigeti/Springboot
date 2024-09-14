package capstone.Entity;

import com.fasterxml.jackson.databind.JsonNode;



public class CustomerProfileMapper {
    public static NewCustomerDto mapToCustomerProfile(JsonNode jsonNode) {
        NewCustomerDto profile = new NewCustomerDto();
        // Basic Information
        profile.setSsnNumber(jsonNode.get("ssnNumber").asLong());
        profile.setFirstName(jsonNode.get("firstName").asText());
        profile.setMiddleName(jsonNode.get("middleName").asText());
        profile.setLastName(jsonNode.get("lastName").asText());
        profile.setTitle(jsonNode.get("title").asText());
        profile.setPhoneNumber(jsonNode.get("phoneNumber").asText());
        profile.setInternationalPhoneNumber(jsonNode.get("internationalPhoneNumber").asText());
        profile.setEmailAddress(jsonNode.get("emailAddress").asText());
        // Citizenship and Residency
        profile.setCitizenship1(jsonNode.get("citizenship1").asText());
        profile.setCitizenship2(jsonNode.get("citizenship2").asText());
        profile.setResidentCountry(jsonNode.get("residentCountry").asText());
        profile.setPoliticallyInfluencedPerson(jsonNode.get("politicallyInfluencedPerson").asText());
        profile.setFinancialObjective(jsonNode.get("financialObjective").asText());

        // Address Information
        if (jsonNode.has("addresses")) {
            JsonNode addressArray = jsonNode.get("addresses");
            if (!addressArray.isEmpty()) {
                JsonNode address1 = addressArray.get(0);
                profile.setAddressLine1(address1.get("addressLine1").asText());
                profile.setAddressLine2(address1.get("addressLine2").asText());
                profile.setCity(address1.get("city").asText());
                profile.setState(address1.get("state").asText());
                profile.setCountry(address1.get("country").asText());
                profile.setZipCode(address1.get("zipCode").asText());
            }
            if (addressArray.size() > 1) {
                JsonNode address2 = addressArray.get(1);
                profile.setAddress2Line1(address2.get("addressLine1").asText());
                profile.setAddress2Line2(address2.get("addressLine2").asText());
                profile.setCity2(address2.get("city").asText());
                profile.setState2(address2.get("state").asText());
                profile.setCountry2(address2.get("country").asText());
                profile.setZipCode2(address2.get("zipCode").asText());
                profile.setInternationalAddressIndicator2(
                        Boolean.parseBoolean(String.valueOf(address2.get("internationalAddress").asBoolean()))
                );
            }
            if (addressArray.size() > 2) {
                JsonNode address3 = addressArray.get(2);
                profile.setAddress3Line1(address3.get("addressLine1").asText());
                profile.setAddress3Line2(address3.get("addressLine2").asText());
                profile.setCity3(address3.get("city").asText());
                profile.setState3(address3.get("state").asText());
                profile.setCountry3(address3.get("country").asText());
                profile.setZipCode3(address3.get("zipCode").asText());
                profile.setInternationalAddressIndicator3(
                        Boolean.parseBoolean(String.valueOf(address3.get("internationalAddress").asBoolean()))
                );
            }
        }
        return profile;
    }
}