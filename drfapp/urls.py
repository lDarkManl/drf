from django.urls import path

from .views import GetCapitalInfoView, AppView, AppListView, AppCreateView


urlpatterns = [
    path('api/capitals/', GetCapitalInfoView.as_view()),
    path('app/', AppView.as_view(), name='app'),
    path('app/list/', AppListView.as_view(), name='app-list'),
    path('app/create/', AppCreateView.as_view(), name='app-create'),
]



