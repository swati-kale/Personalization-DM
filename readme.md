Personalization Rules
------------------------

Based on incoming customer events, and big data analysis of the incoming data, the decision table makes next actions that can be sent downstream for processing.

Consider the scenario of a customer making a payment online. The payment is made after the payment due date, now based on an intelligent analysis of the customer's past history, we can see that the customer's account is good standing and the late payment associated with the payment made can be waived. This is a message that can be sent downstream so that it can be acted upon. The decision table provides for a compilation of such use cases.

Decision table is evaluated using two objects: Events and TrainingModel.



