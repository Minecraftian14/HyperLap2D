/*
 * ******************************************************************************
 *  * Copyright 2015 See AUTHORS file.
 *  *
 *  * Licensed under the Apache License, Version 2.0 (the "License");
 *  * you may not use this file except in compliance with the License.
 *  * You may obtain a copy of the License at
 *  *
 *  *   http://www.apache.org/licenses/LICENSE-2.0
 *  *
 *  * Unless required by applicable law or agreed to in writing, software
 *  * distributed under the License is distributed on an "AS IS" BASIS,
 *  * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  * See the License for the specific language governing permissions and
 *  * limitations under the License.
 *  *****************************************************************************
 */

package games.rednblack.editor.view.ui.box.resourcespanel.draggable.box;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

import games.rednblack.editor.view.ui.box.UIResourcesBoxMediator;
import games.rednblack.h2d.common.ResourcePayloadObject;

public class ImageResource extends BoxItemResource {


    private final Image payloadImg;
    private final ResourcePayloadObject payload;
    
    public ImageResource(AtlasRegion region) {
    	// this is not changing the behavior of the former constructor
    	// as long as the colors of the super class are not changed
    	this(region, new Color(1, 1, 1, 0.2f), new Color(1, 1, 1, 0.4f), Color.BLACK, Color.BLACK, false);
    }
    
    /**
     * Creates a new image resource from the given {@link AtlasRegion}.
     * 
     * @param region The atlas region for the image resource.
     * @param fillColor The color to fill the background of the image.
     * @param borderColor The standard color of the border. Also used when the mouse is not hovering over the image.
     * @param fillMouseOverColor The color to fill the background of the image when the mouse hovers over the image. Only used if the the parameter <code>highlightWhenMouseOver</code> is set to <code>true</code>.
     * @param borderMouseOverColor The color of the border when the mouse hovers over the image. Only used if the the parameter <code>highlightWhenMouseOver</code> is set to <code>true</code>.
     * @param highlightWhenMouseOver Whether to change the border color when the mouse hovers over the image.
     */
    public ImageResource(AtlasRegion region, Color fillColor, Color borderColor, Color fillMouseOverColor, Color borderMouseOverColor, boolean highlightWhenMouseOver) {
    	super(fillColor, borderColor, fillMouseOverColor, borderMouseOverColor, highlightWhenMouseOver);
    	
        Image img = new Image(region);
        if (img.getWidth() > thumbnailSize || img.getHeight() > thumbnailSize) {
            // resizing is needed
            float scaleFactor = 1.0f;
            if (img.getWidth() > img.getHeight()) {
                //scale by width
                scaleFactor = 1.0f / (img.getWidth() / thumbnailSize);
            } else {
                scaleFactor = 1.0f / (img.getHeight() / thumbnailSize);
            }
            img.setScale(scaleFactor);

            img.setX((getWidth() - img.getWidth() * img.getScaleX()) / 2);
            img.setY((getHeight() - img.getHeight() * img.getScaleY()) / 2);
        } else {
            // put it in middle
            img.setX((getWidth() - img.getWidth()) / 2);
            img.setY((getHeight() - img.getHeight()) / 2);
        }

        addActor(img);
        
        setClickEvent(UIResourcesBoxMediator.IMAGE_LEFT_CLICK, UIResourcesBoxMediator.IMAGE_RIGHT_CLICK, this, region.name);

        payloadImg = new Image(region);
        payload = new ResourcePayloadObject();
        payload.name = region.name;
        payload.className = getClass().getName();
    }

    @Override
    public Actor getDragActor() {
        return payloadImg;
    }

    @Override
    public ResourcePayloadObject getPayloadData() {
        return payload;
    }
    
}
