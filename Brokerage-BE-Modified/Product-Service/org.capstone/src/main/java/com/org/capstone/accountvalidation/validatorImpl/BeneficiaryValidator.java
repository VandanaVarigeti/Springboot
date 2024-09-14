//package com.org.capstone.accountvalidation.validatorImpl;
//
//import com.org.capstone.accountvalidation.validators.BeneficiaryValid;
//import com.org.capstone.ennum.AllowedRoles;
//import com.org.capstone.ennum.ProductType;
//import com.org.capstone.ennum.RegistrationType;
//import com.org.capstone.entity.Account;
//import com.org.capstone.entity.Beneficiaries;
//import jakarta.validation.ConstraintValidator;
//import jakarta.validation.ConstraintValidatorContext;
//
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.List;
//
//public class BeneficiaryValidator implements ConstraintValidator<BeneficiaryValid, Account> {
//
//
//
//    @Override
//    public boolean isValid(Account account, ConstraintValidatorContext constraintValidatorContext) {
//        if(ProductType.SIMPL.equals(account.getProductType()) && RegistrationType.INDIVIDUAL.equals(account.getRegistartionType())){
//           if(account.getMinRoles() == 1 && account.getMaxRoles() ==1){
//               return true;
//           }
//        }
//        if(ProductType.SIMPL.equals(account.getProductType()) && RegistrationType.JOINT_TENANT.equals(account.getRegistartionType())){
//            if(account.getMinRoles() == 2 && account.getMaxRoles() ==2){
//                return true;
//            }
//        }
//
//
//        return false;
//    }
//}
