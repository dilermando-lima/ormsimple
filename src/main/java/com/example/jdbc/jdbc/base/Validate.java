package com.example.jdbc.jdbc.base;

public abstract class Validate extends ValidateUtils {
    
    protected void validateAnnotationsEntity(Class<?> classEntity) throws Exception{

        validateTableNotation(classEntity);
        validateTypeAllowedOnFields(classEntity);
        validateFieldNotationsEntity(classEntity);

    }


}
