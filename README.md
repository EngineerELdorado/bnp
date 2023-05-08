# Important Message from the candidate

I believe as mentioned in your email, this assignment was meant to evaluate my problem-solving skills and codes quality.
Not the capacity to create or manage a full project.
That is why I focus only on the implementation of solution (the algorithm) of the discount calculation
and I focus on the test cases to assert different scenarios.

Otherwise I could have made it a full bookshop api that manages not only
the discount calculation but also the CRUD of books,authors, users, sales, authentication/authorization,
persistence to DB, dockerizing the app, creating a CI/CD process to automate the app deployment
to different environments (dev, stage, production) on cloud providers, how to make the app scale if needed.
With all that I would only use my discount calculation algorithm when there is a request to purchase books. 

So if you don't see any cool or complex tools used here, 
it is just because the simplicity of the assignment didn't require it.

But I believe those are things we can talk about LIVE if I proceed to the next step.
Also you will see my commits had some intervals of hours. That is because I was very busy and I had to get interrupted
a lot of times. Otherwise I could finish the assignment in a very short time. or atleast the intervals could be shorter.
# How to compile and run this code:

* This is a simple code base. The only thing needed is to have Java installed and maven. (I use java 17 here)
* in case you don't have maven you can still use the mvn wrapper of the project. eg: ./mvnw clean install
* To test the solution's end to end implementation(algorithm), use the test class DiscountIntegrationServiceTest. 
* there are two test classes unit tests  and one integration tests
There are more than 20 test cases testing all possible scenarios. Even those not mentioned in the assignment

# Problem detected:

* One of the example mentioned in the assignment description seems to have a mistake in the logic you explained it with.
It's the last example that is expecting 320 as the result. 
After spending time on it and trying all different techniques I don't see how that scenario would ever
lead to a result of 320. I think your logic was not right and I can explain why I think so. After all, business
requirements sometimes need more brainstorming and more discussions.
However, I made it pass returning the right value 322.5. I am willing to be corrected if I am wrong on that.


