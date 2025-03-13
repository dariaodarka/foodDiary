package com.project.foodDiary.model.util;

import com.project.foodDiary.entity.enums.Activity;
import com.project.foodDiary.entity.enums.Aim;
import com.project.foodDiary.entity.enums.Gender;
import com.project.foodDiary.model.dto.UserDTO;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.validation.BindingResult;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.project.foodDiary.entity.enums.Activity.*;

public class ValidatorDTO {
    public static List<String> chekErrors(BindingResult bindingResult) {
        List<String> errors = bindingResult.getAllErrors().stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .toList();
        return errors;
    }

    public static List<String> validateUserDTO(UserDTO userDTO) {
        ArrayList<String> errors = new ArrayList<>();
        if(!userDTO.getActivity().toUpperCase().equals(LOW.toString()) &&
                !userDTO.getActivity().toUpperCase().equals(HIGH.toString()) &&
                !userDTO.getActivity().toUpperCase().equals(Activity.MEDIUM.toString()) &&
                !userDTO.getActivity().toUpperCase().equals(VERY_HIGH.toString())){
            errors.add("Пожалуйста укажите коректную активность. Возможные варианты " + Arrays.stream(Activity.values()).toList());
        }
        if (!userDTO.getAim().toUpperCase().equals(Aim.MAINTENANCE.toString()) &&
                !userDTO.getAim().toUpperCase().equals(Aim.WEIGHT_GAIN.toString()) &&
                !userDTO.getAim().toUpperCase().equals(Aim.WEIGHT_LOSS.toString())){
            errors.add("Пожалуйста укажите корректную цель. Возможные варианты " + Arrays.stream(Aim.values()).toList());
        }
        if (!userDTO.getGender().toUpperCase().equals(Gender.MALE.toString()) &&
                !userDTO.getGender().toUpperCase().equals(Gender.FEMALE.toString())){
            errors.add("Пожалуйста укажите корректный пол. Возможные варианты " + Arrays.stream(Gender.values()).toList());
        }
        return errors;
    }
}
