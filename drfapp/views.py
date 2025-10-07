from rest_framework import status
from rest_framework.response import Response
from rest_framework.views import APIView

from .models import Capital
from .serializers import CapitalSerializer


class GetCapitalInfoView(APIView):
    serializer_class = CapitalSerializer

    def get(self, request):
        queryset = Capital.objects.all()
        serializer_for_queryset = self.serializer_class(instance=queryset, many=True)
        return Response(serializer_for_queryset.data)

    def post(self, request):
        serializer_for_writing = self.serializer_class(data=request.data)
        serializer_for_writing.is_valid(raise_exception=True)
        serializer_for_writing.save()
        return Response(serializer_for_writing.data, status=status.HTTP_201_CREATED)
