package com.github.fttroy.testsecurity.records;

import java.util.Date;

public record Otp(String otp, Date expirationDate) {
}
