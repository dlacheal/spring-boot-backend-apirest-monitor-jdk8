package com.utp.springboot.backend.apirest.monitor.auth;

public class JwtConfig {
    public static final String LLAVE_SECRETA = "clave.secreta.123456789";

    public static final String RSA_PRIVADA = "-----BEGIN PRIVATE KEY-----\n" +
            "MIIEugIBADANBgkqhkiG9w0BAQEFAASCBKQwggSgAgEAAoIBAQCbBuMOGBTeTy1g\n" +
            "s3HHcTuHBNyy6srYs9vY0QrLpkB2MYlpOvgH9fhm43DD7Y1efniYZuTtAAC3CFQ3\n" +
            "vvp853t1zl4dhVZsa7CHJNB1LMfYGuPfzUeRwuMQuMVYP6hFOZeFIP1yHJfxD978\n" +
            "lYrAOl5SpSZ4hK+uoityGPf/zbrKZNAs2lycFTyEp2NKDTSvxZhFTz/BvbmTrWzl\n" +
            "jSKmWzSH70Mk/cbBmk3vJMClkmXzPlUa5RlkhCzxIMn6KUQT09fU+zN81UwrWZlP\n" +
            "bSCtzFlgYjnJxkKNEKBLwWj0z+aFaj+9IgNlAi8e6rSsN/ABQRpSGM55WArCvDk9\n" +
            "njZ3XWqdAgMBAAECgf8OcmherYGk8/UhLnX/risVHfqmvQf3i0wjFR4OpNfdA54p\n" +
            "+5QHHQMrTjMAHB3r4rq1RZw8IU0xxJ4xihL4dksZLJM6iaBfix2Frh1TK33Jgf09\n" +
            "m5E25tEXmGdPqJIgz6fR9pBfau/EXPcTdqCTUKM2zAQfg4zdS3cfNCbJwTTWIq4p\n" +
            "PEhVUNUfEwrBw44mLY4htzud36SMq7Y/ehFJ7PR3cq9EgGEkBrBx1uDpxxP3RFde\n" +
            "uAAs8ANt5cdtmxmrkexfJgYZaQxFEC+id3LgRfDQqC60LhwjTPyv10RSsOfS3l2N\n" +
            "Hq3R9EN7uftyGDcoAyPVKJjI0zj9wp3brkSTTdECgYEA1c4PxOZK56nMPNr4BlvQ\n" +
            "zwvWQK3TO0akXO6tASKLOLNuITeefQGPI97wV3DDKJHXchRSdjpjBGiuRpsq7MOw\n" +
            "HvNp9uWSDote8FC90E7IrVlNClZ8eDAFrUpTtIxlYKuAqAK60uZ9nU0BCNhb/fyt\n" +
            "B1WpduaC2L5VCVTrdNZL1NECgYEAuZ834y8B5pzJkhmQz37Slknq2e9b35VxkFJ0\n" +
            "4YmOGSEmVz3C6LHJmhHSOPEHSDspyXIlYMKOfJWeOHkmH8cD3A+VIAQ0FEu5ovDD\n" +
            "gmoyq4CTqxkUjMr+xsx5a7gMWoAhO1pbRT/2Lop27E3dHkfZqg1tWUxV8AKp7Day\n" +
            "HOig3A0CgYAg4Zr9Pimg7TUy2r4wRCid3wUpV1JYGGoovLAwr35XzwiN5T7jnLzF\n" +
            "2ICAAiCRebR3n/Z6pkoaIHtE8pIsbYAFkovmY7INuJN2RASdJ7CnMEev4lMsLL6C\n" +
            "wx6SctEYh+e2bw0KaFZDPdd948BqFFUL/rmcRqEy8jqjv8Kial6pMQKBgAu2LwmH\n" +
            "gsAxLkqokEl3QAQ5f7bmck6GGFc5zKQaULvnKosgm3ahGQ4+h5wOIR5+lSFfsVoM\n" +
            "jH7x1bzMcApbPTQaS5dsjHinWnwcYsmMt8u4XhjSiUhk2nRinFqejzD6mp4rEk0p\n" +
            "PvgqxlwcgZISiHpBRJ/4wAdXs67yuIQ4bUXVAoGAMipyB7u3kb5TijdHGtfB6lr2\n" +
            "wrULwpD02tk2lnqZTOHMYe8tALUS1oU/SqLJmotjsDtro0+s/hc6y3FA7lSWkVu7\n" +
            "Dd9mGJG76pBSovtfRSrKj1+MqDcVDGJ5KP2htHpSnSVhQln26gIULrOGYb7MLdp2\n" +
            "7+en9Zke6Zly3rG+oyI=\n" +
            "-----END PRIVATE KEY-----";

    public static final String RSA_PUBLICA = "-----BEGIN PUBLIC KEY-----\n" +
            "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAmwbjDhgU3k8tYLNxx3E7\n" +
            "hwTcsurK2LPb2NEKy6ZAdjGJaTr4B/X4ZuNww+2NXn54mGbk7QAAtwhUN776fOd7\n" +
            "dc5eHYVWbGuwhyTQdSzH2Brj381HkcLjELjFWD+oRTmXhSD9chyX8Q/e/JWKwDpe\n" +
            "UqUmeISvrqIrchj3/826ymTQLNpcnBU8hKdjSg00r8WYRU8/wb25k61s5Y0ipls0\n" +
            "h+9DJP3GwZpN7yTApZJl8z5VGuUZZIQs8SDJ+ilEE9PX1PszfNVMK1mZT20grcxZ\n" +
            "YGI5ycZCjRCgS8Fo9M/mhWo/vSIDZQIvHuq0rDfwAUEaUhjOeVgKwrw5PZ42d11q\n" +
            "nQIDAQAB\n" +
            "-----END PUBLIC KEY-----";
}
