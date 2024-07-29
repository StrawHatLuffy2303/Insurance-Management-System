<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
 
</head>
<body>
  <h1 style="text-decoration: underline;">Insurance-Management-System</h1>
  <p >Its an Insurance Management System. This system allows for the creation and management of client data, insurance policies, and claims. The system provides a set of APIs that enable users to perform CRUD operations on each of these entities, allowing clients to be created and updated, policies to be managed, and claims to be filed and processed. This System provides a comprehensive solution for managing insurance-related data and operations. By providing a set of APIs for managing client data, insurance policies, and claims, the system can streamline workflows, improve efficiency, and reduce errors.</p>
<hr>
<br>

<h2 style="background-color: green;color: #fff;text-align:center">Prerequisites<h2>
 <hr>
 <li>JDK 8 or higher installed</li>
 <li>Maven installed</li>
 <li>Clone the repository containing your Spring Boot application code.</li>
 <li>Navigate to the root folder of the project in the terminal or command prompt.</li>
 <li>Run the command "mvn clean install" to build the application.</li>
 <li>Once the build is successful, run the command "mvn spring-boot:run" to start the application.</li>
 <li>The application should now be up and running on your local machine.</li>
<br>
 
<h2>Steps  to  Setup  the  Application :</h2> 
 <hr>

<h3 style="text-decoration: underline; color:yellow">1. Open src >main>resources>application.properties</h3>
 <hr>

![appliacatioProperties](![image](https://github.com/user-attachments/assets/68e821ae-2805-4582-b1ca-6c41a702b420)
)

<h3 style="text-decoration: underline; color:yellow">2.Configure your mySql database</h3>
 <hr>
    <li>change server.port</li>
    <li>put your database.username</li>
    <li>put your database.password</li>
<hr>

![configureDB](https://user-images.githubusercontent.com/51885478/230093271-9b960a8c-9f69-4eb1-aa65-c5b102b62459.PNG)

     
<h3 style="text-decoration: underline; color:yellow">3. Create MySql database  : </h3>
 <hr>
         <li>Create mySql database (database name : IMTDB {suggested})</li>
         <li>Use MySql command to create database</li>
          <li>example : 
          <ul>
              <li><code>create database IMTDB</code>;</li>
              <li><code>use IMTDB</code>;</li>
          </ul>
    <br>
<h3  style="text-decoration: underline; color:yellow">4. Run The Application.</h3>
<hr>
     
 
<hr>  
<h2 style="background-color: green;color: #fff;text-align:center">Steps to Execute Api/ Application </h2>  
<h1>How to execute all Api :</h1>
  <h3>Open one of the below link:</h3>
	
 [Swagger (Suggested) : http://localhost:8078/swagger-ui/index.html](http://localhost:8078/swagger-ui/index.html)
 <br>
 [Postman : http://localhost:8078/api/admin](http://localhost:8078/api/admin)

  <h2><strong><em>NOTE: Please   change the port number accordingly and run the application first</em></strong></h2>
  <hr>
  
  <h2 style="background-color: green;color: #fff;text-align:center">Follow the bellow steps one by one :</h2>  
  <hr>
 <h3 style="text-decoration: underline; color:yellow">1.	Admin Registration ( <span style="background-color: green; text-align: center;padding: 3px; border-radius: 2px;">POST</span> /api/admin/)</h3>
     <hr>
      <li>This endpoint allows the creation of a new administrator account.
      <li>The endpoint expects a JSON payload in the request body containing the administrator's details, such as name, email, and password.
      <li>The endpoint should validate the input and create a new administrator account in the database if the input is valid.</li>
<hr>
 <h3 style="text-decoration: underline; color:yellow">2. Admin Login (<span style="background-color: green; text-align: center;padding: 3px; border-radius: 2px;">POST</span>  /api/admin/login)<h3>
   <hr>
     <li>This endpoint allows an existing administrator to log in to their account.</li>
     <li>The endpoint expects a JSON payload in the request body containing the administrator's mobile number and password.</li>
     <li>The endpoint should verify the input against the administrator accounts stored in the database and return a RandomString as a  token upon successful                   authentication.</li>
     <li><strong><em>NOTE: please save the generated uuid as key</em></strong></li>
<hr>


   <h3 style="text-decoration: underline; color:yellow">3. Create Policy (<span style="background-color: green; text-align: center;padding: 3px; border-radius: 2px;">POST</span> /api/policies/)</h3>
   <hr>
<li>This endpoint allows the creation of a new insurance policy.</li>
</li>The endpoint expects a JSON payload in the request body containing the policy details, such :  
             <li>"policy Number": "987456",(must be unique)</li>
             <li>"type": "Medical Insurance",</li>
              <li>"coverageAmount": 40000,(currently its taking hard             coded value)</li>
              <li>"premium": 99,</li>
              <li>"startDate": "2023-04-05",</li>
              <li>"endDate": "2043-04-05"(should be greater then startDate)</li>
              <li>The endpoint should validate the input and create a new policy in the database if the input is valid.</li>
            </li>



<h3 style="text-decoration: underline; color:yellow">4. Register Client (<span style="background-color: green; text-align: center;padding: 3px; border-radius: 2px;">POST</span>  /api/client/)</h3>
<li>This endpoint allows the creation of a new client account.</li>
<li>The endpoint expects a JSON payload in the request body containing the client's details, such as name, email, and password etc.</li>
<li>The endpoint should validate the input and create a new client account in the database if the input is valid.</li>


<h3 style="text-decoration: underline; color:yellow">5. Client Login (<span style="background-color: green; text-align: center;padding: 3px; border-radius: 2px;">POST</span> /api/clients/login/)</h3>
<ul>
	<li>The endpoint allows an existing client to log in to their account.</li>
	<li>The endpoint expects a JSON payload in the request body containing the client's mobile number and password.</li>
	<li>The endpoint should verify the input against the client accounts stored in the database and return a Random String as a token upon successful authentication.</li>
     <li><strong><em>NOTE: please save the generated uuid as key</em></strong></li>
</ul>
  
   <h3 style="text-decoration: underline; color:yellow">6. View All Policies (<span style="background-color: blue; text-align: center;padding: 3px; border-radius: 2px;">GET</span> /api/policies/)</h3>
    <hr>
    <ul>
        <li>The endpoint allows the retrieval of a list of all available insurance policies.</li>
        <li>The endpoint should return a JSON array of all the policies stored in the database.</li>
    </ul>

<h3 style="text-decoration:underline;  color:yellow">7. Buy Policy (<span style="background-color: green; text-align: center;padding: 3px; border-radius: 2px;">POST</span> /api/policies/{id})</h3>
<ul>
	<li>The endpoint allows a client to purchase an insurance policy.</li>
	<li>The endpoint expects a JSON payload in the request body containing the client's details, such as:
        <ul>
        <li>policyNumber as id</li> 
        <li>client uniqueKey as key.</li>
        </ul>
    </li>
	<li>The endpoint should validate the input and update the policy's database record with the client's details if the input is valid.</li>
</ul>

<h3 style="text-decoration:underline;  color:yellow">8. Create Claim (<span style="background-color: green; text-align: center;padding: 3px; border-radius: 2px;">POST</span> /api/claims/)</h3>
	<ul>
		<li>This endpoint allows the creation of a new insurance claim.</li>
		<li>User must buy that policy which they will claim.</li>
		<li>The endpoint expects a JSON payload in the request body containing the claim details, such as:</li>
		<ul>
			<li>policy number</li>
			<li>Client key as key</li>
		</ul>
		<li>The endpoint should validate the input and create a new claim in the database if the input is valid.</li>
	</ul>

   <h3 style="text-decoration:underline;color:yellow">9. View All Claims (<span style="background-color: blue; text-align: center;padding: 3px; border-radius: 2px;">GET</span> /api/clients)</h3>
    <hr>
    <ul>
        <li>This endpoint allows a Admin to view all their insurance claims.</li>
        <li>The endpoint should retrieve all the claims associated with the client's account and return them as a JSON array.</li>
        <li>It take input such as:</li>
        <ul>
            <li>Admin key as key (only admin can access this).</li>
        </ul>
        <li><strong><em>Note: Please copy the generated id as claim id.</em></strong></li>
    </ul>
<hr>

   <h3 style="text-decoration:underline;color:yellow">10. Change Claim Status by Admin (<span style="background-color: orange; text-align: center;padding: 3px; border-radius: 2px; color:#000"><strong>POST</strong></span> /api/claims/{id})</h3>
    <ul>
        <li>This endpoint allows an administrator to update the status of an insurance claim.</li>
        <li>It take inputs such as:</li>
        <ul>
            <li>claim id as id</li>
            <li>Admin key as key (admin access only)</li>
            <li>ClaimStatus (only except OPEN/CLOSED/REJECTED/PENDING/ACCEPTED)</li>
        </ul>
        <li><strong><em>NOTE: Do not write anything in the body</em></strong></li>
        <li>The endpoint expects a JSON payload in the request body containing the updated claim status.</li>
        <li>The endpoint should validate the input and update the claim's database record if the input is valid.</li>
    </ul>
<hr>
	<hr>
<h1 style="background-color: green;color: #fff;text-align:center">Flow of the Application </h1>
<hr>
	
![InsurenceManagementFlowchart_page-0001 (1)](https://user-images.githubusercontent.com/51885478/230147146-5f3e8464-9147-44fd-b56b-483f2ed56743.jpg)

<br>
<hr>
    <h2 style="background-color: green;color: #fff;text-align:center">Some additional Api are </h2>
    <hr>
     <p>Get Claim by ID : <span style="background-color: blue; text-align: center;padding: 3px; border-radius: 2px;">Get</span> <code>api/claims/{id}</code>; (admin and client both)</p>
     <p>Delete Claim by ID : <span style="background-color: red; text-align: center;padding: 3px; border-radius: 2px;">DELETE</span>  <code>api/claims/{id}</code>; (admin access)
    </p>
    <hr>
     <p>Get all Clients : <span style="background-color: blue; text-align: center;padding: 3px; border-radius: 2px;">GET</span><code>api/clients/</code>; (admin access)</p>
     <p>Get client by ID : <span style="background-color: blue; text-align: center;padding: 3px; border-radius: 2px;">GET</span><code> api/clients/{id}</code> (admin and client both)</p>
     <p>Upadte Clientd By ID : <span style="background-color:orange; text-align: center;padding: 3px; border-radius: 2px;">PUT</span> <code>api/clients/id</code> (admin and client both)</p>
     <p>Delete Clients By ID : <span style="background-color: red; text-align: center;padding: 3px; border-radius: 2px;">DELETE</span> <code>api/clients/{id}</code> (currently only admin)</p>
     <hr>
     <p>Delete policy By ID : <span style="background-color: red; text-align: center;padding: 3px; border-radius: 2px;">DELETE</span> <code>api/policies/{id}</code> (only admin access)</p>
     <p>Update policy by ID : <span style="background-color: orange; text-align: center;padding: 3px; border-radius: 2px;">PUT</span> <code>api/policies/{id}</code> (only admin access)</p>
     <p>Get All policy : <span style="background-color: blue; text-align: center;padding: 3px; border-radius: 2px;">GET</span> <code>api/policies/</code>  (admin and clients both)</p>
     <hr>
     <p>Admin logout : <span style="background-color: green; text-align: center;padding: 3px; border-radius: 2px;">POST</span> <code>api/admin/logout</code></p>
     <p>Client logout : <span style="background-color: green; text-align: center;padding: 3px; border-radius: 2px;">POST</span> <code>api/clients/logout</code></p>
     

<hr>
    <h2 style="background-color: green;color: #fff;text-align:center">Database Schema </h2>
    <hr>

   ![Untitled (3)](https://user-images.githubusercontent.com/51885478/230126695-c8e74376-3cbf-46f7-9396-979e8dfb4df6.png)

<h1>Insurance Management System Features</h1>
	<h2>Client Management:</h2>
	<ul>
		<li>View all clients: <code>/api/clients</code></li>
		<li>View a specific client: <code>/api/clients/{id}</code></li>
		<li>Create a new client: <code>/api/clients</code></li>
		<li>Update a client: <code>/api/clients/{id}</code></li>
		<li>Delete a client: <code>/api/clients/{id}</code></li>
	</ul>
	<h2>Insurance Policy Management:</h2>
	<ul>
		<li>View all insurance policies: <code>/api/policies</code></li>
		<li>View a specific insurance policy: <code>/api/policies/{id}</code></li>
		<li>Create a new insurance policy: <code>/api/policies</code></li>
		<li>Update an insurance policy: <code>/api/policies/{id}</code></li>
		<li>Delete an insurance policy: <code>/api/policies/{id}</code></li>
	</ul>
	<h2>Claim Management:</h2>
	<ul>
		<li>View all claims: <code>/api/claims</code></li>
		<li>View a specific claim: <code>/api/claims/{id}</code></li>
		<li>Create a new claim: <code>/api/claims</code></li>
		<li>Update a claim: <code>/api/claims/{id}</code></li>
		<li>Delete a claim: <code>/api/claims/{id}</code></li>
	</ul>
	<h2>Additional Features:</h2>
	<ul>
		<li>Unique key concept for authorized access</li>
		<li>Data validation for accurate data entry</li>
		<li>Error handling for graceful exception handling</li>
		<li>Integration with other systems or APIs for comprehensive solutions</li>
	</ul>
	<p>The Insurance Management System provides a comprehensive solution for managing insurance-related data and operations. With a robust set of APIs for managing clients, insurance policies, and claims, the system can streamline workflows, improve efficiency, and reduce errors.</p>

</body>
</html>
