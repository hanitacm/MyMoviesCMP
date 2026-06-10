package com.hanitacm.mymoviescmp.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.hanitacm.mymoviescmp.domain.model.Movie

@Composable
fun MovieList(movies: List<Movie>, onMovieClick: (Int) -> Unit) {
    LazyVerticalGrid(contentPadding = PaddingValues(4.dp), columns = GridCells.Adaptive(150.dp)) {
        items(items = movies) { item ->
            MovieListItem(
                modifier = Modifier.padding(4.dp),
                item = item,
                onClick = { onMovieClick(item.id) },
            )
        }
    }
}

@Composable
private fun MovieListItem(modifier: Modifier, item: Movie, onClick: () -> Unit) {
    Card(shape = RoundedCornerShape(4.dp), modifier = modifier) {
        Column(Modifier.clickable { onClick() }) {
            AsyncImage(
                model = "https://image.tmdb.org/t/p/w185${item.posterPath}",
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier.height(229.dp),
            )
            Column(modifier = Modifier.padding(8.dp).fillMaxSize()) {
                RatingList(item.voteAverage)
                Text(
                    modifier = Modifier.padding(top = 8.dp).fillMaxWidth(),
                    text = item.title,
                    maxLines = 2,
                    minLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    style = MaterialTheme.typography.headlineLarge,
                )
            }
        }
    }
}

@Composable
private fun RatingList(rate: Double) {
    Row(modifier = Modifier.height(18.dp), verticalAlignment = Alignment.CenterVertically) {
        //        Image(
        //            modifier = Modifier.padding(end = 8.dp),
        //            painter = painterResource(id = R.drawable.ic_star),
        //            contentDescription = null,
        //        )
        Text(modifier = Modifier.fillMaxWidth(), text = rate.toString())
    }
}

@Preview(device = "id:Nexus 5")
@Composable
fun MovieListPreview() {
    val list = mutableListOf<Movie>()
    repeat(20) {
        list.add(
            Movie(
                popularity = 2000.0,
                voteAverage = 0.0,
                overview =
                    $$"A professional thief with $40 million in debt and his family's life on the line must commit one final heist" +
                        " - rob a futuristic airborne casino filled with the world's most dangerous criminals.",
                posterPath = "/6CoRTJTmijhBLJTUNoVSUNxZMEI.jpg",
                releaseDate = "2020-09-29",
                title = "Money Plane",
                originalTitle = "Money Plane",
                originalLanguage = "en",
                backdropPath = "/gYRzgYE3EOnhUkv7pcbAAsVLe5f.jpg",
                id = 34,
            )
        )
    }
    MaterialTheme { MovieList(list) {} }
}
