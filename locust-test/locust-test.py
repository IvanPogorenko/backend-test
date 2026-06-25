from locust import HttpUser, task
import random

class ApiUser(HttpUser):
    def on_start(self):
        self.token = "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbiIsImV4cCI6MTc4NDkyNDQ3OCwiYXV0aCI6IlJPTEVfQURNSU4gUk9MRV9VU0VSIiwiaWF0IjoxNzgyMzMyNDc4LCJ1c2VySWQiOjF9.5fj7olKSvZbSET9zqBUKqtOt6AnnABjLhLv-BV6I1mHSkV39Op4pH9HjDzqJndDF1CoxKXRV_VS3riOUVyo8mw"

    @task(5)
    def get_locations(self):
        self.client.get(
            "/api/locations",
            headers={
                "Authorization": f"Bearer {self.token}",
                "accept": "*/*",
                "Content-Type": "application/json"
            }
            )

    @task(3)
    def patch_jobs(self):
        min_salary = random.randint(1000, 5000)
        max_salary = random.randint(min_salary, min_salary + 5000)
        self.client.patch(
            "/api/jobs/1",
            headers={
                "Authorization": f"Bearer {self.token}",
                "accept": "*/*",
                "Content-Type": "application/json"
            },
            json={
                "id": 1,
                "jobTitle": "Developer",
                "minSalary": min_salary,
                "maxSalary": max_salary,
                "employeeId": 1
            }
            )