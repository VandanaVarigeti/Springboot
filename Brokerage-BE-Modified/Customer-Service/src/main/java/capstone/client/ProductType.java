package capstone.client;

import lombok.Getter;

@Getter
public enum ProductType {
    SIMPL ("Simpl"),
    RETIREMENT("Retirement") ;

    private final String description;

     ProductType(String description){
        this.description = description;
    }
}
