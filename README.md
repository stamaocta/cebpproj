# cebpproj


Stock Exchange

This project will model a system that handles the mediation of sellers, buyers and the offers that they publish. The system revolves around a central resource “bucket”, called the screen. Every participant to the stock market interacts with the screen in a number of ways:

-   Modify an offer that is not “occupied”
    
-   Issue new offers
    
-   Engage in transactions with other participants.
    

The screen will store the participants’ offers, providing them to whoever “reads” the screen, and blocking people from interacting with it when an offer is being modified. The participants’ goal is to match their offers with others, and they do this by reading the offers from the screen, and listing their own.

When two offers match and the conditions for a transaction to occur are satisfied (both participants are free), they will engage in the transaction, closing or possibly modifying (still want to sell/buy more) their offer, which will prompt the screen to update.

Possible problems

-   The screen might not be able to update if there are ongoing transactions. The screen should issue its intention to update, and wait for all transactions to complete before doing so.
    

	-   Alternatively, transactions that issue updates after they are finished should release the screen from waiting for a transaction to finish, so that they may join the other pending updates, to avoid an inter-dependency.
    

-   Two buyers should not be able to take the same offer. Offers should block during a transaction, rejecting whoever tries to access them, or possibly putting them in a queue in the case where there are still units left for sale after the current transaction ends.
    
-   Participants reading the screen as updates are about to be issued might end up learning redundant data. Reading should be a non-blocking operation since it doesn’t change anything, so multiple participants will be able to read at the same time.
    
-   Updates should only block the resources they pertain to, so that different updates on different resources should go through at the same time. Should blocking readers block the whole screen from being read, or just certain resources?
    
-   The screen might reach its capacity and no more offers may be added. This blocks any attempts to insert data, but not to update. This might force participants to update their offers so that the throughput resumes, or flat-out bounce offers back to the participants, to be re-added later, allowing fresh offers in that might find matches.
    

Classes

-   Participants model buyers and sellers.
    
-   Offers could be depicted through a flag (1 to buy and 0 to sell), a resource and a price.
    
-   The screen would be globally visible and handle data synchronization.
    

Methods

-   Participants may prompt the screen to give them the latest information.
    
-   Participants may issue updates to offers they own, which propagate to the screen and may block certain information.
    
-   Participants may engage in transactions if they find matching offers.
    

Entry Points

Main entry point, handling initialization of the screen, ensuring transparency and globalness of the information offered, as well as the initial availability for it to be updated.

Participant entry point, handling the logic for engaging in transactions, adding or updating offers.

Screen limit entry point, where the screen engages in logic with participants, issuing forced updates to offers or perhaps rejecting offers back to participants.
