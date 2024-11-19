package com.projeto.mobileglobal.endereco

import com.google.android.gms.maps.model.LatLng

object PlacesData {
    val places = listOf(
        Place(
            name = "Av. Paulista Shopping",
            latLng = LatLng(-23.563843, -46.6552535),
            address = "Av. Paulista, 1230 - Bela Vista, São Paulo - SP, 01310-100",
            type = "Type 2: 1 Conector"
        ),
        Place(
            name = "Eletroposto PortoSeguro",
            latLng = LatLng(-23.5618359, -46.6598663),
            address = "Av. Brigadeiro Luís Antônio, 3383 - Jardim Paulista, São Paulo - SP, 01401-001",
            type = "Type 2: 2 Conector"
        ),
        Place(
            name = "Garagem Trianon",
            latLng = LatLng(-23.5643163, -46.6569618),
            address = "Alameda Jaú, 850 - Jardim Paulista, São Paulo - SP, 01420-001",
            type = "Type 2: 1 Conector"
        ),
        Place(
            name = "BYD Dahruj Jardins",
            latLng = LatLng(-23.5715494, -46.6930854),
            address = "R. Colômbia, 753 - Jardim Europa, São Paulo - SP, 01438-001",
            type = "Type 2: 5 Conector"
        ),
        Place(
            name = "Top Center Shopping",
            latLng = LatLng(-23.565874, -46.6520448),
            address = "Av. Paulista, 854 - Bela Vista, São Paulo - SP, 01311-100",
            type = "Type 2: 5 Conector"
        )
    )
}
