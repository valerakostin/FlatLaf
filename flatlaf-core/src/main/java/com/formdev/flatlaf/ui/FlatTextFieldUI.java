/*
 * Copyright 2019 FormDev Software GmbH
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.formdev.flatlaf.ui;

import static com.formdev.flatlaf.util.UIScale.scale;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import javax.swing.JComponent;
import javax.swing.UIManager;
import javax.swing.plaf.ComponentUI;
import javax.swing.plaf.basic.BasicTextFieldUI;
import javax.swing.text.JTextComponent;

/**
 * Provides the Flat LaF UI delegate for {@link javax.swing.JTextField}.
 *
 * TODO document used UI defaults of superclass
 *
 * @uiDefault Component.focusWidth				int
 *
 * @author Karl Tauber
 */
public class FlatTextFieldUI
	extends BasicTextFieldUI
{
	protected int focusWidth;

	private final Handler handler = new Handler();

	public static ComponentUI createUI( JComponent c ) {
		return new FlatTextFieldUI();
	}

	@Override
	protected void installDefaults() {
		super.installDefaults();

		focusWidth = UIManager.getInt( "Component.focusWidth" );
	}

	@Override
	protected void installListeners() {
		super.installListeners();

		getComponent().addFocusListener( handler );
	}

	@Override
	protected void uninstallListeners() {
		super.uninstallListeners();

		getComponent().removeFocusListener( handler );
	}

	@Override
	protected void paintBackground( Graphics g ) {
		JTextComponent c = getComponent();

		FlatUIUtils.paintParentBackground( g, c );

		Graphics2D g2 = (Graphics2D) g.create();
		try {
			FlatUIUtils.setRenderingHints( g2 );

			float focusWidth = (c.getBorder() instanceof FlatBorder) ? scale( (float) this.focusWidth ) : 0;

			g2.setColor( c.getBackground() );
			FlatUIUtils.fillRoundRectangle( g2, 0, 0, c.getWidth(), c.getHeight(), focusWidth, 0 );
		} finally {
			g2.dispose();
		}
	}

	//---- class Handler ------------------------------------------------------

	private class Handler
		implements FocusListener
	{
		@Override
		public void focusGained( FocusEvent e ) {
			getComponent().repaint();
		}

		@Override
		public void focusLost( FocusEvent e ) {
			getComponent().repaint();
		}
	}
}
