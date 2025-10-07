from django.db import models
from django.contrib.auth import get_user_model

User = get_user_model()


class Capital(models.Model):
    country = models.CharField(max_length=100)
    capital_city = models.CharField(max_length=100)
    capital_population = models.PositiveIntegerField()
    author = models.ForeignKey(User, on_delete=models.CASCADE, related_name='capitals')

    def __str__(self) -> str:
        return f"{self.capital_city} ({self.country})"
