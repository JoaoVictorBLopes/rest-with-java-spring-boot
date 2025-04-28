package br.com.projetos.controller;

import br.com.projetos.exception.UnsupportedMathOperationException;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
@RestController
@RequestMapping("/math")
public class MathController {

    @RequestMapping("/sum/{numberOne}/{numberTwo}")
    public Double sum(@PathVariable("numberOne") String numberOne, @PathVariable("numberTwo")String numberTwo) throws Exception {
        if (!isNumeric(numberOne) || !isNumeric(numberTwo)) throw new UnsupportedMathOperationException("Please set a numeric value!");
        return convertToDouble(numberOne) + convertToDouble(numberTwo);
    }

    private Double convertToDouble(String strnumber) throws IllegalArgumentException {
        if (strnumber == null || strnumber.isEmpty()) throw new UnsupportedMathOperationException("Please set a numeric value!");
        String number = strnumber.replace(",", ".");
        return Double.parseDouble(number);
    }

    private boolean isNumeric(String strnumber) {
        if (strnumber == null || strnumber.isEmpty()) return true;
        String number = strnumber.replace(",", ".");
        return number.matches("[-+]?[0-9]*\\.?[0-9]+");
    }

}
