package com.org.capstone.accountvalidation.validatorImpl;

import com.org.capstone.accountvalidation.validators.ValidProductType;
import com.org.capstone.ennum.AllowedRoles;
import com.org.capstone.ennum.ProductType;
import com.org.capstone.ennum.RegistrationType;
import com.org.capstone.entity.Account;
import com.org.capstone.entity.Beneficiaries;
import com.org.capstone.entity.JointTenant;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.List;

public class ProductTypeValidator implements ConstraintValidator<ValidProductType, Account> {
    private static final int MAX_JT = 2;
    private static final int MAX_BENEFICIARY = 4;
    private static final int TOTAL_PERCENTAGE = 100;

    @Override
    public boolean isValid(Account account, ConstraintValidatorContext constraintValidatorContext) {
        if (account == null) {
            return false;
        }

        ProductType productType = account.getProductType();
        RegistrationType registrationType = account.getRegistrationType();
        AllowedRoles allowedRoles = account.getAllowedRoles();


        List<JointTenant> joint_tenant = account.getJointTenant();
        List<Beneficiaries> beneficiaries = account.getBeneficiaries();


        if (ProductType.SIMPL.equals(productType)) {
            if (RegistrationType.INDIVIDUAL.equals(registrationType) || RegistrationType.JOINT_TENANT.equals(registrationType)) {
                if (RegistrationType.INDIVIDUAL.equals(registrationType) && AllowedRoles.INDIVIDUAL.equals(allowedRoles)) {
                    if ((joint_tenant != null && !joint_tenant.isEmpty()) || (beneficiaries != null && !beneficiaries.isEmpty())) {
                        constraintValidatorContext.disableDefaultConstraintViolation();
                        constraintValidatorContext.buildConstraintViolationWithTemplate("No Joint_Tenant and Beneficiaries are allowed for Individual account")
                                .addPropertyNode("Beneficiary OR Joint_Tenant")
                                .addConstraintViolation();
                        return false;
                    }
                    return true;
                } else {
                    if (RegistrationType.JOINT_TENANT.equals(registrationType) && AllowedRoles.JT.equals(allowedRoles)) {
                        if (beneficiaries == null || beneficiaries.isEmpty() ) {
//
                            return true;
                        }
                        if (joint_tenant == null || joint_tenant.isEmpty()) {
                            constraintValidatorContext.disableDefaultConstraintViolation();
                            constraintValidatorContext.buildConstraintViolationWithTemplate("Please add Joint_Tenant to create Joint_Tenant Account")
                                    .addPropertyNode("Joint_Tenant")
                                    .addConstraintViolation();
                            return false;
                        }

                        if (joint_tenant.size() == MAX_JT) {
                            return true;
                        } else {
                            constraintValidatorContext.disableDefaultConstraintViolation();
                            constraintValidatorContext.buildConstraintViolationWithTemplate("MIN & MAX Tenant 2 allowed")
                                    .addPropertyNode("JT")
                                    .addConstraintViolation();
                            return false;
                        }

                    }
                }

            }
            return false;


        } else if (ProductType.RETIREMENT.equals(productType)) {
            if (RegistrationType.RETIREMENT_ACCOUNT.equals(registrationType) && AllowedRoles.INDIVIDUAL.equals(allowedRoles)) {
                if ((joint_tenant != null && !joint_tenant.isEmpty()) || (beneficiaries != null && !beneficiaries.isEmpty())) {
                    constraintValidatorContext.disableDefaultConstraintViolation();
                    constraintValidatorContext.buildConstraintViolationWithTemplate("No Joint_Tenant and Beneficiaries are allowed for Individual account")
                            .addPropertyNode("Beneficiary OR Joint_Tenant")
                            .addConstraintViolation();
                    return false;
                }
                return true;

            } else {
                if (RegistrationType.RETIREMENT_ACCOUNT.equals(registrationType) && AllowedRoles.BENEFICIARY.equals(allowedRoles)) {
                    if (joint_tenant == null || joint_tenant.isEmpty()) {
//                        constraintValidatorContext.disableDefaultConstraintViolation();
//                        constraintValidatorContext.buildConstraintViolationWithTemplate("JOINT_TENANT are not allowed to create Beneficiary Account")
//                                .addPropertyNode("JOINT_TENANT")
//                                .addConstraintViolation();
                        return true;
                    }
                    if (beneficiaries == null || beneficiaries.isEmpty()) {
                        constraintValidatorContext.disableDefaultConstraintViolation();
                        constraintValidatorContext.buildConstraintViolationWithTemplate("Please Add Beneficiaries to create Beneficiary Account")
                                .addPropertyNode("Beneficiary")
                                .addConstraintViolation();
                        return false;
                    }
                    if (beneficiaries.size() > MAX_BENEFICIARY) {
                        constraintValidatorContext.disableDefaultConstraintViolation();
                        constraintValidatorContext.buildConstraintViolationWithTemplate("Max 4 Beneficiary allowed")
                                .addPropertyNode("Beneficiary")
                                .addConstraintViolation();
                        return false;

                    }

                    int TotalPercentage = beneficiaries.stream().mapToInt(Beneficiaries::getPercentage).sum();

                    if (TotalPercentage == TOTAL_PERCENTAGE) {
                        return true;
                    } else {
                        constraintValidatorContext.disableDefaultConstraintViolation();
                        constraintValidatorContext.buildConstraintViolationWithTemplate("Total percentage need to be 100")
                                .addPropertyNode("Percentage")
                                .addConstraintViolation();
                        return false;
                    }

                }
            }

        }
        return false;


    }


}

