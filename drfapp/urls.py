from django.urls import path

from .views import GetCapitalInfoView


urlpatterns = [
    path('api/capitals/', GetCapitalInfoView.as_view()),
]


