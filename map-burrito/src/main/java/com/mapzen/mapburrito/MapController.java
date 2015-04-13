package com.mapzen.mapburrito;

import org.oscim.backend.AssetAdapter;
import org.oscim.layers.Layer;
import org.oscim.layers.tile.buildings.BuildingLayer;
import org.oscim.layers.tile.vector.OsmTileLayer;
import org.oscim.layers.tile.vector.VectorTileLayer;
import org.oscim.layers.tile.vector.labeling.LabelLayer;
import org.oscim.map.Map;
import org.oscim.theme.ThemeFile;
import org.oscim.tiling.source.oscimap4.OSciMap4TileSource;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;

public class MapController {
    private Map map;
    private VectorTileLayer baseLayer;

    public MapController(Map map) {
        this.map = map;
    }

    public Map getMap() {
        return map;
    }

    public VectorTileLayer getBaseLayer() {
        return baseLayer;
    }

    /**
     * Set tile source via URL string.
     */
    public MapController setTileSource(String url) {
        setTileSource(new OSciMap4TileSource(url));
        return this;
    }

    /**
     * Set tile source via {@link OSciMap4TileSource}
     */
    public MapController setTileSource(OSciMap4TileSource tileSource) {
        baseLayer = new OsmTileLayer(map);
        baseLayer.setTileSource(tileSource);
        map.setBaseMap(baseLayer);
        return this;
    }

    /**
     * Add generic map layer.
     */
    public MapController addLayer(Layer layer) {
        map.layers().add(layer);
        return this;
    }

    /**
     * Convenience method to add building layer using current base layer.
     */
    public MapController addBuildingLayer() {
        addLayer(new BuildingLayer(map, baseLayer));
        return this;
    }

    /**
     * Convenience method to add label layer using current base layer.
     */
    public MapController addLabelLayer() {
        addLayer(new LabelLayer(map, baseLayer));
        return this;
    }

    /**
     * Set map theme using path to xml style declaration (usually found in assets folder).
     */
    public MapController setTheme(String path) {
        setTheme(new Theme(path));
        return this;
    }

    /**
     * Set map theme using {@link Theme} object.
     */
    public MapController setTheme(Theme theme) {
        map.setTheme(theme);
        return this;
    }

    /**
     * Implementation of {@link ThemeFile} that loads xml style declaration from the given path.
     */
    public static class Theme implements ThemeFile {
        private final String path;

        public Theme(String path) {
            this.path = path;
        }

        @Override
        public InputStream getRenderThemeAsStream() throws FileNotFoundException {
            return AssetAdapter.readFileAsStream(path);
        }

        public String getPath() {
            return path;
        }
    }
}