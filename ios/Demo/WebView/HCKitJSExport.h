//
//  BotsKitJSExport.h
//  BotsItoa
//
//  Created by 刘海川 on 2019/1/1.
//  Copyright © 2019 刘海川. All rights reserved.
//

#import <Foundation/Foundation.h>
#import <JavaScriptCore/JavaScriptCore.h>

NS_ASSUME_NONNULL_BEGIN

@protocol HCKitJSExport <JSExport>
JSExportAs(camera,
           - (void)cameraWithResult:(JSValue *)result
           );
@end

NS_ASSUME_NONNULL_END
