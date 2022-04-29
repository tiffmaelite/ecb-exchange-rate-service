# ecb-exchange-rate-service

**DISCLAIMER: Was implemented for a (failed) job interview - It contains bad code and is not maintained - DO NOT REUSE**


Run <code>mvn clean package && mvn spring-boot:run</code> from the command line

* To see today's foreign conversion rate from EUR to the currency of your choice, go to:
<http://localhost:8080/api/getConversionRate?currency=&lt;ISO4217-currency-code&gt;>

* To see a past (max. 90 days) foreign conversion rate from EUR to the currency of your choice, go to:
<http://localhost:8080/api/getPastConversionRate?currency=&lt;ISO4217-currency-code&gt;&date=&lt;yyyy-mm-dd&gt;>
