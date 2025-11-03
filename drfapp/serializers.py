from rest_framework import serializers
from django.contrib.auth import get_user_model
from .models import Capital


User = get_user_model()


class CapitalSerializer(serializers.ModelSerializer):
    author = serializers.SlugRelatedField(
        slug_field='username', queryset=User.objects.all()
    )
    country = serializers.CharField(max_length=100)

    class Meta:
        model = Capital
        fields = (
            'capital_city',
            'capital_population',
            'author',
            'country',
        )


